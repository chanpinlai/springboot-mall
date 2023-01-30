package com.jakgcc.springbootmall.controller;

import com.jakgcc.springbootmall.dto.LombokDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LombokController {
    @GetMapping("/testLombok")
    public LombokDTO testLombok(){
        LombokDTO lombokDTO = LombokDTO.builder().id(123).name("LCB").build();
        return lombokDTO;
    }

}
