package com.wtacademyplatform.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wtacademyplatform.backend.dto.ItemDto;
import com.wtacademyplatform.backend.dto.ResponseDto;
import com.wtacademyplatform.backend.dto.SaveItemDto;
import com.wtacademyplatform.backend.entities.Item;
import com.wtacademyplatform.backend.services.ItemService;

@RequestMapping("/item")
@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public List<ItemDto> findAllItems() {
        return this.itemService.findAllItems();
    }

    @GetMapping("/{id}")
    public Optional<Item> findItemById(@PathVariable("id") long id) {
        return this.itemService.findItemById(id);
    }

    @PostMapping("/create")
    public ResponseDto create(@RequestBody SaveItemDto saveItemDto) {
        return this.itemService.create(saveItemDto);
    }

    @PutMapping("/update/{id}")
    public ResponseDto update(@PathVariable("id") long id, @RequestBody SaveItemDto saveItemDto) {
        return this.itemService.update(id,saveItemDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable("id") long id) {
        return this.itemService.delete(id);
    }
}
