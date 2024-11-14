package com.da_thao.project_test.Controller;

import com.da_thao.project_test.config.CURDMethodsInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController implements CURDMethodsInterface<Employees> {

    private int idCounter = 6;
    private List<Employees> employees = new ArrayList<>(
            Arrays.asList(
                    new Employees(1, LocalDate.of(2001, 12, 4), "Nam", 10000000, "Hồ Trung"),
                    new Employees(2, LocalDate.of(2004, 5, 18), "Nữ", 20000000, "Ngọc Mai"),
                    new Employees(3, LocalDate.of(2005, 11, 11), "Nữ", 5000000, "Dạ Thảo"),
                    new Employees(4, LocalDate.of(1989, 12, 11), "Nam", 510000000, "Ko Pin Yi"),
                    new Employees(5, LocalDate.of(2000, 12, 12), "Nam", 110000000, "Ferder Gorst")

            )
    );

    @Override
    @GetMapping("")
    public ResponseEntity<List<Employees>> getAll() {
        return ResponseEntity.ok(employees);
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Employees employee) {
        Map<String, Object> response = new HashMap<>();

        // Kiểm tra các trường không được để trống
        if (employee.getFullName() == null || employee.getFullName().isEmpty() ||
                employee.getBirthDay() == null ||
                employee.getGender() == null || employee.getGender().isEmpty() ||
                employee.getSalary() <= 0) {

            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", "Thông tin nhân viên không hợp lệ. Vui lòng điền đầy đủ thông tin.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        employee.setId(idCounter++);
        employees.add(employee);
        response.put("status", HttpStatus.CREATED);
        response.put("employee", employee);
        response.put("message", "Tạo nhân viên thành công.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Employees> getById(@PathVariable int id) {
        Employees employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        return (employee != null)
                ? ResponseEntity.ok(employee)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        Employees employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (employee == null) {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "Nhân viên không tồn tại.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        employees.remove(employee);
        response.put("status", HttpStatus.OK);
        response.put("message", "Nhân viên với ID " + id + " đã được xóa thành công.");
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable int id, @RequestBody Employees updatedEmployee) {
        Map<String, Object> response = new HashMap<>();
        Employees existingEmployee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (existingEmployee == null) {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "Nhân viên không tồn tại.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (updatedEmployee.getFullName() == null || updatedEmployee.getFullName().isEmpty() ||
                updatedEmployee.getBirthDay() == null ||
                updatedEmployee.getGender() == null || updatedEmployee.getGender().isEmpty() ||
                updatedEmployee.getSalary() <= 0) {

            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", "Thông tin cập nhật không hợp lệ. Vui lòng điền đầy đủ thông tin.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Cập nhật thông tin nhân viên
        existingEmployee.setFullName(updatedEmployee.getFullName());
        existingEmployee.setBirthDay(updatedEmployee.getBirthDay());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setSalary(updatedEmployee.getSalary());

        response.put("status", HttpStatus.OK);
        response.put("message", "Nhân viên với ID " + id + " đã được cập nhật thành công.");
        response.put("employee", existingEmployee);
        return ResponseEntity.ok(response);
    }
}
