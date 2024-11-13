package com.da_thao.project_test.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping({"/", "/{Name}"})
    public Map<String, Object> sendGreeting(@PathVariable(required = false) String Name) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "hello " + (Name != null ? Name : ""));
        response.put("status", HttpStatus.OK);

        return response;
    }



}
