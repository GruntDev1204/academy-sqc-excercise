package com.da_thao.project_test.Controller;


import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DictionaryController {
    @GetMapping("/dictionary/{word}")
    public ResponseEntity<Map<String, Object>> transalte(@PathVariable String word) {
        Map<String, Object> response = new HashMap<>();

        String transformWord = word.toLowerCase().replaceAll("\\s+", "");

        if (word.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Từ ko được để trống"));
        }

        String result;

        switch (transformWord) {
            case "hello":
                result = "Xin chào";
                break;
            case "computer":
                result = "Máy tính";
                break;
            case "music":
                result = "Âm nhạc";
                break;
            case "programmer":
                result = "Lập trình viên";
                break;
            default:
                result = "chưa có trong từ điển";
                break;
        }
        response.put("status", HttpStatus.OK);
        response.put("result", result);
        return ResponseEntity.ok(response);

    }

}
