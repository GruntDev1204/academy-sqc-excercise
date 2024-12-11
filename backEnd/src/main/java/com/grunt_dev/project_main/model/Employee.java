package com.grunt_dev.project_main.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "salary", columnDefinition = "decimal(15,2)")
    double salary;

    @Column(name = "full_name", columnDefinition = "varchar(50)")
    String fullName;

    @Column(name = "gender")
    String gender;

    @Column(name = "birthday")
    LocalDate birthDay;

    @Column(columnDefinition = "varchar(10)")
    String phoneNumber;

    @Column(columnDefinition = "varchar(500)", nullable = true)
    String avatar;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    Department department;
}
