package com.baltan.springboot.controller;

import com.baltan.springboot.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019-01-18 15:47
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/greet")
    public String greet() {
        asyncService.greet();
        return "数据处理完毕……";
    }
}
