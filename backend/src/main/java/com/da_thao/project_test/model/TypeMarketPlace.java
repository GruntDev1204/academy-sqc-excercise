package com.da_thao.project_test.model;

import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TypeMarketPlace {
    Integer id;
    String name;
}