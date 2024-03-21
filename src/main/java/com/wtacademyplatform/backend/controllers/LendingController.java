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

import com.wtacademyplatform.backend.dto.LendingDto;
import com.wtacademyplatform.backend.dto.ResponseDto;
import com.wtacademyplatform.backend.dto.SaveLendingDto;
import com.wtacademyplatform.backend.entities.Lending;
import com.wtacademyplatform.backend.services.LendingService;

@RequestMapping("/lending")
@RestController
public class LendingController {
    private final LendingService lendingService;

    public LendingController(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    @GetMapping("/all")
    public List<LendingDto> findAllLendings() {
        return this.lendingService.findAllLendings();
    }

    @GetMapping("/{id}")
    public Optional<Lending> findLendingById(@PathVariable("id") long id) {
        return this.lendingService.findLendingById(id);
    }

    @PutMapping("/endLending/{id}")
    public ResponseDto endLending(@PathVariable("id") long id) {
        return this.lendingService.endLending(id);
    }
  
    @PostMapping("/create")
    public ResponseDto create(@RequestBody SaveLendingDto saveLendingDto) {
        return this.lendingService.create(saveLendingDto);
    }

    @PutMapping("/update/{id}")
    public ResponseDto update(@PathVariable("id") long id, @RequestBody SaveLendingDto saveLendingDto) {
        return this.lendingService.update(id,saveLendingDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable("id") long id) {
        return this.lendingService.delete(id);
    }
}
