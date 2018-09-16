package com.emailManager.controller;

import com.emailManager.enums.ENUM;
import com.emailManager.dao.DaoService;
import com.emailManager.logicService.*;
import com.emailManager.logicService.SendEmailThread;
import com.emailManager.entity.*;
import com.emailManager.enums.SUCCESS;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Microservice RESTful API: to receive and reply to http request calls remotely.
 */
@CrossOrigin
@RestController
public class MessageController {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    DaoService daoService;
    @Autowired
    Helper helper;
    private AddressHistory emailHistory;
    ENUM enumMsg;

    /**
     * @param request: HttpServletRequest
     * @param message: {"email":"*","name":"*","body":"*"} in JSON format
     * @return {"JSON result message"}
     */
    @CrossOrigin
    @RequestMapping(value = "/email" ,consumes = MediaType.APPLICATION_JSON_VALUE ,method = {RequestMethod.POST,RequestMethod.GET})
    public Object email(HttpServletRequest request, @RequestBody Message message, HttpServletResponse response) {
        if((enumMsg = helper.validate(request, message)) instanceof SUCCESS){

            emailHistory = daoService.getEmailHistory(message.getEmail());
            OutBox outBox = new OutBox(emailSender, message);

            if(emailHistory == null){
                enumMsg = outBox.sendEmail();
            }
            else if((enumMsg = emailHistory.dailyMaxMsg()) instanceof SUCCESS){
                    new Thread(new SendEmailThread(outBox)).start();
            }

            if(enumMsg instanceof SUCCESS){
                daoService.updateDB(request, emailHistory, message);
                helper.responseAddJwtToken(request, response);
            }
        }
        return helper.response(enumMsg);
    }

    /**
     * You could test this micro service from your browser
     * @return
     */
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE )
    public Object browserTest(){
        return ImmutableMap.<String, Object>builder().
                put(SUCCESS.STATUS.toString(), SUCCESS.MICRO_SERVICE_ON.toString()).build();
    }

}



