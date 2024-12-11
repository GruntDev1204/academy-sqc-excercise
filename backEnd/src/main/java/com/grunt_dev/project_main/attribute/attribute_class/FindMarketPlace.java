package com.grunt_dev.project_main.attribute.attribute_class;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FindMarketPlace {
    String name;
    String address;
    Long typeId;
    String rentPrice;
    Double area;
    LocalDate minStartDate;
    LocalDate maxStartDate;
}
