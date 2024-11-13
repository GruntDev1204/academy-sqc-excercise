package com.da_thao.project_test.Controller;

public class Student {
    private int id;
    private double score;
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Student() {
    }

    public Student(int id, String fullName, double score) {
        this.id = id;
        this.fullName = fullName;
        this.score = score;
    }


}
