package com.da_thao.project_test.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Employee {
    Integer id;
    double salary;
    String fullName;
    String gender;
    LocalDate birthDay;
    String phoneNumber;
    Integer departmentId;
    String avatar;
}
