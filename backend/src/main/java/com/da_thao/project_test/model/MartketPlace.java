package com.da_thao.project_test.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MartketPlace {
    Integer id;
    String name;
    String address;
    Integer typeId;
    Double area;
    Double rentPrice;
    LocalDate startDate;
    String nameType;
}

