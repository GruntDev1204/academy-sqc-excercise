package com.da_thao.project_test.dto.filter_information;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FindMarketPlace {
    Integer id;
    String name;
    String address;
    String typeId;
    String rentPrice;
    Double area;
    LocalDate minStartDate;
    LocalDate maxStartDate;
}
