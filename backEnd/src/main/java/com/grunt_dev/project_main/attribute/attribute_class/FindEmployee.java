package com.grunt_dev.project_main.attribute.attribute_class;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class FindEmployee {
    String fullName;           // Tên
    LocalDate dobFrom;     // Ngày sinh từ
    LocalDate dobTo;       // Ngày sinh đến
    String gender;         // Giới tính
    String salaryValue;    // mức lương
    String phoneNumber;    // Số điện thoại
    Integer departmentId;  // Bộ phận
}
