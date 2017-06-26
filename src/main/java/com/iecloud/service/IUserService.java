package com.iecloud.service;


import com.iecloud.common.ServerResponse;
import com.iecloud.pojo.User;

/**
 * Created by geely
 */
public interface IUserService {

    ServerResponse<User> login(String phoneNumber, String password);

    ServerResponse<String> sendVerificationCode(String phoneNumber, String VerificationCode);

    ServerResponse<String> register(User user, String verificationCode, String verificationCodeInSession);

    ServerResponse<String> checkValid(String phoneNumber, String verificationCode, String verificationCodeInSession);

    ServerResponse<String> forgetResetPassword(User user, String verificationCode, String verificationCodeInSession);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);
}
