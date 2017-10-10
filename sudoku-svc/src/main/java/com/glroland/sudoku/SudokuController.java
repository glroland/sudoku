package com.glroland.sudoku.ocr;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuController {

    @RequestMapping("/")
    public String index() {
        return "Hello World!";
    }
}

