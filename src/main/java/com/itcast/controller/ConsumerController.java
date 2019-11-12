package com.itcast.controller;

import com.itcast.kafka.consumer.Running;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * consumer的控制类
 * @author 拼命三石
 * @version 1.0
 */
@Controller
@RequestMapping("/consumer")
public class ConsumerController {

    /**
     * 获取Topic主题内容信息
     * @param request
     * @param model
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("/getMessage")
    public String getMessage(HttpServletRequest request, Model model) throws ExecutionException, InterruptedException {

        String index = request.getParameter("index");

        model.addAttribute("message", Running.getMessage("" + index + ""));

        return "getMessage";
    }
}
