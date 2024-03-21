package com.wtacademyplatform.backend.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wtacademyplatform.backend.dto.LendingDto;
import com.wtacademyplatform.backend.dto.ResponseDto;
import com.wtacademyplatform.backend.dto.SaveLendingDto;
import com.wtacademyplatform.backend.entities.Item;
import com.wtacademyplatform.backend.entities.Lending;
import com.wtacademyplatform.backend.entities.User;
import com.wtacademyplatform.backend.enums.Role;
import com.wtacademyplatform.backend.repositories.ILendingRepository;

@Service
public class LendingService {
    private final ILendingRepository repo;

    private final UserService userService;

    private final ItemService itemService;

    public LendingService(ILendingRepository repository, UserService userService, ItemService itemService) {
        this.repo = repository;
        this.userService = userService;
        this.itemService = itemService;
    }

    public List<LendingDto> findAllLendings(){
        List<Lending> lendings = this.repo.findAll();
        return lendings.stream().map(Lending::ToDto).collect(Collectors.toList());
    }

    public Optional<Lending> findLendingById(long id) {
        return this.repo.findById(id);
    }

    public ResponseDto endLending(long id) {
        Optional<Lending> lendingOptional = this.repo.findById(id);
        if (lendingOptional.isEmpty()) {
            return new ResponseDto(false, "Geen lending met ID '" + id + "' gevonden.");
        }
        Lending lending = lendingOptional.get();

        Item item = lending.getItem();
        item.setAvailable(true);
        lending.setEndDate(Instant.now());
        lending.setItem(item);

        return new ResponseDto(true, this.repo.save(lending).ToDto());
    }

    public ResponseDto create(SaveLendingDto saveLendingDto) {
        Optional<Item> itemOptional = itemService.findItemById(saveLendingDto.getItemId());
        if (itemOptional.isEmpty()) {
            return new ResponseDto(false, "Geen item meegegeven.");
        }
        Item item = itemOptional.get();
        if (!item.isAvailable()){
            return new ResponseDto(false, "Boek is al uitgeleend.");
        }

        Optional<User> adminOptional = userService.findUserById(saveLendingDto.getAdminId());
        if (adminOptional.isEmpty()) {
            return new ResponseDto(false, "Geen Admin meegegeven.");
        }
        User admin = adminOptional.get();
        if(admin.getRole() != Role.ADMIN){
            return new ResponseDto(false, "User die het boek toewijst is geen Admin.");
        }

        Optional<User> lendingUserOptional = userService.findUserById(saveLendingDto.getLendingUserId());
        if (lendingUserOptional.isEmpty()) {
            return new ResponseDto(false, "Geen lendingUser meegegeven.");
        }
        User lendingUser = lendingUserOptional.get();

        item.setAvailable(false);
        Lending lending = new Lending();
        lending.setItem(item);
        lending.setAdmin(admin);
        lending.setLendingUser(lendingUser);

        return new ResponseDto(true, this.repo.save(lending).ToDto());
    }

    public ResponseDto update(long id, SaveLendingDto saveLendingDto) {
        Optional<Lending> lendingOptional = this.repo.findById(id);
        if (lendingOptional.isEmpty()) {
            return new ResponseDto(false, "Geen lending met ID '" + id + "' gevonden.");
        }

        Lending updatedLending = lendingOptional.get();

        if(itemService.findItemById(saveLendingDto.getItemId()).isPresent()) {
            updatedLending.setItem(itemService.findItemById(saveLendingDto.getItemId()).get());
        }
        if(userService.findUserById(saveLendingDto.getLendingUserId()).isPresent()) {
            updatedLending.setLendingUser(userService.findUserById(saveLendingDto.getLendingUserId()).get());
        }
        if(userService.findUserById(saveLendingDto.getAdminId()).isPresent()) {
            updatedLending.setAdmin(userService.findUserById(saveLendingDto.getAdminId()).get());
        }

        return new ResponseDto(true,this.repo.save(updatedLending).ToDto());
    }

    public ResponseDto delete(long id) {
        Optional<Lending> lendingOptional = this.repo.findById(id);
        if (lendingOptional.isEmpty()) {
            return new ResponseDto(false, "Geen lending met ID '" + id + "' gevonden.");
        }

        this.repo.deleteById(id);
        return new ResponseDto(true, null);
    }
}
