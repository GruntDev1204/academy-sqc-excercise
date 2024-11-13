package com.da_thao.project_test.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class StudentController {


    private List<Student> students = new ArrayList<>(
            Arrays.asList(
                    new Student(1, "Hồ trung", 9.7),
                    new Student(2, "Hồ trung", 9.7),
                    new Student(3, "Hồ trung", 9.7)
            )
    );

    @RequestMapping("/student/getAll")
    public List<Student> getAllStudents() {
        return students;
    }







    @RequestMapping("/student/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable int id) {
        for (Student st : students) {
            if (id == st.getId()) {
                return ResponseEntity.ok(st); // 200 OK with the student object as JSON
            }
        }
        // Return 404 Not Found with a JSON error message if the student is not found
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"error\": \"Student not found\"}");
    }


}
