package com.iecloud.controller.portal;

import com.iecloud.annotation.AopClassAnno;
import com.iecloud.annotation.AopMethodAnno;
import com.iecloud.common.Const;
import com.iecloud.common.ResponseCode;
import com.iecloud.common.ServerResponse;
import com.iecloud.pojo.User;
import com.iecloud.service.IUserService;
import com.iecloud.util.DigitalVerificationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user/")
@AopClassAnno(testString = "testClassAnno")


public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "test.do",method = RequestMethod.POST)
    @ResponseBody
    public double testError(){
        log.error("ERROR");
        log.info("INFO");
        log.debug("debug");
        return 9/0;
    }

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String phoneNumber, String password, HttpSession session){
        if(session == null) {
            return ServerResponse.createByErrorMessage("session为空");
        }
        ServerResponse<User> response = iUserService.login(phoneNumber,password);
        if(response.isSuccess()){
            System.out.println("OK");
            System.out.println(response.getData());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "sendVerificationCode.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> sendVerificationCode(String phoneNumber, HttpSession session) {
        String verificationCode = DigitalVerificationCode.getVerificationCode();
        ServerResponse<String> response = iUserService.sendVerificationCode(phoneNumber, verificationCode);
        if (response.isSuccess()) {
            session.setAttribute(Const.V_CODE, verificationCode);
        }
        System.out.println(response.getMsg());
        return response;
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user, String verificationCode, HttpSession session){
        if(session == null) {
            return ServerResponse.createByErrorMessage("session为空");
        }
        String verificationCodeInSession = (String) session.getAttribute(Const.V_CODE);
        return iUserService.register(user, verificationCode, verificationCodeInSession);
    }

    @RequestMapping(value = "checkValid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String phoneNumber, String verificationCode, HttpSession session){
        if(session == null) {
            return ServerResponse.createByErrorMessage("session为空");
        }
        String verificationCodeInSession = (String) session.getAttribute(Const.V_CODE);
        return iUserService.checkValid(phoneNumber, verificationCode, verificationCodeInSession);
    }


    @RequestMapping(value = "getUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

    @RequestMapping(value = "forgetResetPassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(User user, String verificationCode, HttpSession session){
        if(session == null) {
            return ServerResponse.createByErrorMessage("session为空");
        }

        String verificationCodeInSession = (String) session.getAttribute(Const.V_CODE);
        return iUserService.forgetResetPassword(user, verificationCode, verificationCodeInSession);
    }



    @RequestMapping(value = "resetPassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }


    @RequestMapping(value = "updateInformation.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session,User user){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "getInformation.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }






























}
