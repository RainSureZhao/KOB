package com.kob.backend.Controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getbotinfo/")
    public String getBotInfo() {
        return  "hhhhh";
    }
}
