package com.da_thao.project_test.dto.data_filter;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarketPlaceFilter {
    Integer id;
    String name;
    String address;
    String nameType;
    Double rentPrice;
    Double area;
    LocalDate minStartDate;
    LocalDate maxStartDate;
}
