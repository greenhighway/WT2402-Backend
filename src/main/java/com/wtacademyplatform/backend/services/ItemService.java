package com.wtacademyplatform.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wtacademyplatform.backend.dto.ItemDto;
import com.wtacademyplatform.backend.dto.ResponseDto;
import com.wtacademyplatform.backend.dto.SaveItemDto;
import com.wtacademyplatform.backend.entities.Book;
import com.wtacademyplatform.backend.entities.Item;
import com.wtacademyplatform.backend.enums.Condition;
import com.wtacademyplatform.backend.repositories.IItemRepository;

@Service
public class ItemService {
    private final IItemRepository repo;

    private final BookService bookService;

    public ItemService(IItemRepository repository, BookService bookservice) {
        this.repo = repository;
        this.bookService = bookservice;
    }

    public List<ItemDto> findAllItems(){
        List<Item> items = this.repo.findAll();
        return items.stream().map(Item::ToDto).collect(Collectors.toList());
    }

    public Optional<Item> findItemById(long id) {
        return this.repo.findById(id);
    }

    public List<Item> findItemsByBookId(long id) {
        return this.repo.findAll().stream()
                .filter(item -> id == item.getBook().getId())
                .toList();
    }

    public ResponseDto create(SaveItemDto saveItemDto) {
        Optional<Book> bookOptional = bookService.findBookById(saveItemDto.getBookId());

        if (bookOptional.isEmpty()) {
            return new ResponseDto(false, null);
        }

        Book book = bookOptional.get();

        Item item = new Item();
        item.setAvailable(saveItemDto.isAvailable());
        item.setItemCondition(Condition.valueOf(saveItemDto.getItemCondition()));
        item.setBook(book);

        // check if the book has no items, if not then start with 1.
        List<Item> foundItems = this.findItemsByBookId(book.getId());

        if (foundItems.isEmpty()) {
            item.setWtKey(book.getId() + "." + 1);
            return new ResponseDto(true, this.repo.save(item));
        }

        // if the book already has items, get the last item id and increment with 1.
        long lastItemId = Integer.parseInt(foundItems.get(foundItems.size() - 1).getWtKey().split("\\.")[1]) + 1;
        item.setWtKey(book.getId() + "." + lastItemId);

        return new ResponseDto(true, this.repo.save(item).ToDto());
    }

    public ResponseDto update(long id, SaveItemDto saveItemDto) {
        Optional<Item> itemOptional = this.repo.findById(id);
        if (itemOptional.isEmpty()) {
            return new ResponseDto(false, null);
        }

        Item updatedItem = itemOptional.get();

        updatedItem.setAvailable(saveItemDto.isAvailable());
        updatedItem.setItemCondition(Condition.valueOf(saveItemDto.getItemCondition()));

        return new ResponseDto(true,this.repo.save(updatedItem).ToDto());
    }

    public ResponseDto delete(long id) {
        Optional<Item> itemOptional = this.repo.findById(id);
        if (itemOptional.isEmpty()) {
            return new ResponseDto(false, null);
        }

        this.repo.deleteById(id);
        return new ResponseDto(true, null);
    }
}
