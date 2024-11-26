package com.da_thao.project_test.model;

import com.da_thao.project_test.model.type_rotation.GenderConverter;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @Column(name = "salary", columnDefinition = "decimal(15,2)")
    double salary;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "gender")
    @Convert(converter = GenderConverter.class)
    String gender;

    @Column(name = "birthday")
    LocalDate birthDay;
    @Column(name = "phone_number")

    String phoneNumber;
    String avatar;

    @ManyToOne
    @JoinColumn(name = "department_id" , nullable = true)
    Department department;
}
