
package com.grunt_dev.project_main.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "type_market_places")
public class TypeMarketPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
}
