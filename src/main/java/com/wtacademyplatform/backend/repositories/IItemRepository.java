package com.wtacademyplatform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtacademyplatform.backend.entities.Item;

public interface IItemRepository extends JpaRepository<Item,Long> {
}