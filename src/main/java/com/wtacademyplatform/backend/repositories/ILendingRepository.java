package com.wtacademyplatform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtacademyplatform.backend.entities.Lending;

public interface ILendingRepository extends JpaRepository<Lending,Long> {
}
