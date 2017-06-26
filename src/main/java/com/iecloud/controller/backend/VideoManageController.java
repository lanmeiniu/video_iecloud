package com.iecloud.controller.backend;

import com.iecloud.common.Const;
import com.iecloud.common.ResponseCode;
import com.iecloud.common.ServerResponse;
import com.iecloud.pojo.User;
import com.iecloud.pojo.Video;
import com.iecloud.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/6/21.
 */
@Controller
@RequestMapping("/manage/video")
public class VideoManageController {

    @Autowired
    private IVideoService iVideoService;

    @RequestMapping(value="uploadVideoRecord.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> uploadVideoRecord(Video video, HttpSession session){
//        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
//        } else if (user.getRole() == Const.Role.ROLE_CUSTOMER) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NO_OPERATION_AUTHORITY.getCode(), ResponseCode.NO_OPERATION_AUTHORITY.getDesc());
//        }
        return iVideoService.insertVideoRecord(video);
    }

}
