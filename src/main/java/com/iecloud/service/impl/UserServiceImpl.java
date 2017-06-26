package com.iecloud.service.impl;


import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.iecloud.common.AliyunSms;
import com.iecloud.common.Const;
import com.iecloud.common.ServerResponse;
import com.iecloud.dao.UserMapper;
import com.iecloud.pojo.User;
import com.iecloud.service.IUserService;
import com.iecloud.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by geely
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AliyunSms aliyunSmsService;


    @Override
    public ServerResponse<User> login(String phoneNumber, String password) {
        int resultCount = userMapper.checkPhone(phoneNumber);
        if(resultCount == 0 ){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        System.out.println(md5Password);
        User user  = userMapper.selectLogin(phoneNumber, md5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        System.out.println(md5Password);
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<String> sendVerificationCode(String phoneNumber, String verificationCode) {
        aliyunSmsService.setPhoneNumber(phoneNumber);
        aliyunSmsService.setVerificationcode(verificationCode);
        try {
            //发短信
            SendSmsResponse response = aliyunSmsService.sendSms();
            Thread.sleep(200);
            if(response.getCode() != null && response.getCode().equals("OK")) {
                return ServerResponse.createBySuccessMessage("验证码发送成功");
            } else {
                return ServerResponse.createByErrorMessage("验证码发送失败");
            }

        } catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("客户端故障");
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("其他故障");
        }
    }

    public ServerResponse<String> register(User user, String verificationCode, String verificationCodeInSession){
        ServerResponse validResponse = this.checkValid(user.getUsername(), verificationCode, verificationCodeInSession);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<String> checkValid(String phoneNumber, String verificationCode, String verificationCodeInSession){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(phoneNumber) &&
                org.apache.commons.lang3.StringUtils.isNotBlank(verificationCode)){
            //开始校验
            int resultCount = userMapper.checkPhone(phoneNumber);
            if(resultCount > 0 ){
                return ServerResponse.createByErrorMessage("用户名已存在");
            }

            if (!verificationCode.equalsIgnoreCase(verificationCodeInSession)) {
                return ServerResponse.createByErrorMessage("验证码错误");
            }
        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    public ServerResponse<String> forgetResetPassword(User user, String verificationCode,String verificationCodeInSession){
        if(org.apache.commons.lang3.StringUtils.isBlank(verificationCode)){
            return ServerResponse.createByErrorMessage("参数错误，验证码需要传递");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(verificationCodeInSession)) {
            return ServerResponse.createByErrorMessage("请先获取验证码");
        }

        int resultCount = userMapper.checkPhone(user.getPhone());
        if(resultCount == 0 ){
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        if (!verificationCodeInSession.equalsIgnoreCase(verificationCode)) {
            return ServerResponse.createByErrorMessage("验证码错误");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(user.getPassword());
        int rowCount = userMapper.updatePasswordByPhone(user.getPhone(), md5Password);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("修改密码成功");
        } else {
            return ServerResponse.createByErrorMessage("修改密码失败");
        }
    }


    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }


    public ServerResponse<User> updateInformation(User user){
        //username是不能被更新的
        //email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.

        User updateUser = new User();
        updateUser.setId(user.getId());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }



    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);

    }

    //backend
    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }



}
