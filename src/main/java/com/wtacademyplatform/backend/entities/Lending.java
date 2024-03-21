package com.wtacademyplatform.backend.entities;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wtacademyplatform.backend.dto.LendingDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @CreationTimestamp
    private Instant startDate;

    @Column()
    private Instant endDate;

    @ManyToOne
    private Item item;

    @JsonIgnore
    @ManyToOne
    private User lendingUser;

    @JsonIgnore
    @ManyToOne
    private User admin;

    public LendingDto ToDto(){
        return new LendingDto(id, admin.ToDto(), lendingUser.ToDto(), item.ToDto(), startDate, endDate);
    }
}
