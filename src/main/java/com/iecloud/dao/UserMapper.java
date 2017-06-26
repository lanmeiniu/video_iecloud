package com.iecloud.dao;

import com.iecloud.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkPhone(String phoneNumber);

    User selectLogin(@Param("phone") String phone, @Param("password")String password);

    int updatePasswordByPhone(@Param("phone")String phone,@Param("passwordNew")String passwordNew);

    int checkPassword(@Param(value="password")String password,@Param("userId")Integer userId);
}