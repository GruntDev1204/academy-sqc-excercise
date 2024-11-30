package com.grunt_dev.project_main.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "market_places")
public class MarketPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(columnDefinition = "Varchar(40)")
    String name;
    String address;

    Double area;
    @Column(columnDefinition = "Varchar(500)")
    String avatar;

    @Column(name = "rent_pice", columnDefinition = "decimal(25,2)")
    Double rentPrice;

    @Column(name = "rent_date")
    LocalDate rentDate;

    @Column(columnDefinition = "Varchar(500)")
    String description;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = true)
    TypeMarketPlace typeMarketPlace;
}

