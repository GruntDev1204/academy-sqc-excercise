package com.da_thao.project_test.Controller;


import com.da_thao.project_test.request.CalculatorRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/caculator")

public class CaculatorController {

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> caculator(@RequestBody CalculatorRequest request) {

        // Lấy dữ liệu từ DTO
        double a = request.getNumber1();
        double b = request.getNumber2();
        String operator = request.getOperator();

        // Kiểm tra nếu có bất kỳ tham số nào là null hoặc không hợp lệ
        if (operator == null || operator.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Thiếu toán tử"));
        }

        double result = 0;
        String message = "";

        // Tạo một Map để chứa kết quả
        Map<String, Object> response = new HashMap<>();

        // Kiểm tra toán tử và thực hiện phép tính
        switch (operator) {
            case "+":
                result = a + b;
                message = "Kết quả phép cộng: " + result;
                break;
            case "-":
                result = a - b;
                message = "Kết quả phép trừ: " + result;
                break;
            case "*":
                result = a * b;
                message = "Kết quả phép nhân: " + result;
                break;
            case "/":
                if (b == 0) {
                    message = "Không thể chia cho 0!";
                    response.put("status", HttpStatus.BAD_REQUEST);
                    response.put("message", message);
                    return ResponseEntity.badRequest().body(response);  // Trả về lỗi nếu chia cho 0
                }
                result = a / b;
                message = "Kết quả phép chia: " + result;
                break;
            default:
                message = "Toán tử không hợp lệ!";
                response.put("status", HttpStatus.BAD_REQUEST);
                response.put("message", message);
                return ResponseEntity.badRequest().body(response);  // Trả về lỗi nếu toán tử không hợp lệ
        }

        response.put("status", HttpStatus.OK);
        response.put("result", result);
        response.put("message", message);
        return ResponseEntity.ok(response);  // Trả kết quả tính toán
    }


}




