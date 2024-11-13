package com.da_thao.project_test.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private int idCounter = 5;  // Giá trị bắt đầu từ 4 vì đã có 3 nhân viên mẫu
    private List<Employees> employees = new ArrayList<>(
            Arrays.asList(
                    new Employees( 1 , LocalDate.of(2001 , 12 , 04),"Nam", 10000000, "Hồ Trung"  ),
                    new Employees( 2 , LocalDate.of(2004 , 5 , 18) ,"Nữ", 20000000, "Ngọc Mai"  ),
                    new Employees( 3 , LocalDate.of(2005 , 11 , 11) ,"Nữ", 5000000, "Dạ Thảo"  ),
                    new Employees( 4 , LocalDate.of(1989 , 12 , 11) ,"Nam", 510000000, "Ko Pin Yi"  )
                    )
    );

    @GetMapping("")
    public ResponseEntity<List<Employees>> getAllEmployees() {
        return ResponseEntity.ok(employees); // HTTP 200 OK
    }


    @PostMapping("")
    public ResponseEntity<Employees> addEmployee(@RequestBody Employees employee) {
        // Tự động gán ID cho nhân viên mới
        employee.setId(idCounter++);
        employees.add(employee);

        // Trả về nhân viên vừa tạo
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Employees> getEmployeeById(@PathVariable int id) {


        // Tìm kiếm nhân viên theo ID
        Employees employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }


        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();

        Employees employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        employees.remove(employee);

        response.put("status", HttpStatus.OK);
        response.put("message", "Nhân viên với ID " + id + " đã được xóa thành công.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable("id") int id, @RequestBody Employees updatedEmployee) {
        Map<String, Object> response = new HashMap<>();

        Employees existingEmployee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (updatedEmployee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        existingEmployee.setFullName(updatedEmployee.getFullName());
        existingEmployee.setBirthDay(updatedEmployee.getBirthDay());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setSalary(updatedEmployee.getSalary());

        response.put("status", HttpStatus.OK);
        response.put("message", "Nhân viên với ID " + id + " đã được cập nhật thành công.");
        response.put("employee", existingEmployee);  // Trả về thông tin nhân viên sau khi cập nhật
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
