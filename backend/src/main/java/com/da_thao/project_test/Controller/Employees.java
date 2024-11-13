package com.da_thao.project_test.Controller;

import java.time.LocalDate;

public class Employees {
    private int id;
    private double salary;
    private String fullName;
    private String gender;
    private LocalDate birthDay;


    public Employees(int id, LocalDate birthDay, String gender, double salary, String fullName) {
        this.id = id;
        this.birthDay = birthDay;
        this.gender = gender;
        this.salary = salary;
        this.fullName = fullName;
    }

    public Employees() {
    }


    public double getSalary() {
        return salary;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }







}
