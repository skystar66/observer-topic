package com.topic.customer.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {


    /**
     * 登录
     */

    @RequestMapping("/login")
    public String login() {

        return "success";
    }


    /**
     * 登出
     */
    @RequestMapping("/logout")
    public String logout() {


        return "success";
    }


    /**
     * 订阅
     */
    @RequestMapping("/sub")
    public String sub() {

        return "success";
    }

    /**
     * 取消订阅
     */
    @RequestMapping("/cancel")
    public String cancel() {

        return "success";
    }


}
