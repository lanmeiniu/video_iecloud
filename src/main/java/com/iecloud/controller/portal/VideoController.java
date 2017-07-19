package com.iecloud.controller.portal;

import com.iecloud.common.Const;
import com.iecloud.common.ServerResponse;
import com.iecloud.pojo.HistoricalRecord;
import com.iecloud.pojo.User;
import com.iecloud.pojo.Video;
import com.iecloud.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
@Controller
@RequestMapping("/video/")
public class VideoController {

    @Autowired
    private IVideoService iVideoService;

    @RequestMapping(value = "searchVideo.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Video>> searchVideo(String keyWord) {

        return iVideoService.searchVideo(keyWord);
    }

    @RequestMapping(value = "insertVideoRecord.do")
    @ResponseBody
    public ServerResponse<String> insertVideoRecord(Video video){
        return iVideoService.insertVideoRecord(video);
    }


    @RequestMapping(value = "updateRecord.do")
    @ResponseBody
    public  ServerResponse<String> updateRecord(Video video){
        return iVideoService.updateRecord(video);
    }

    @RequestMapping(value = "deleteVideoRecord.do")
    @ResponseBody
    public ServerResponse<String> deleteVideoRecord (String id) {
        return iVideoService.deleteVideoRecord(id);
    }

    @RequestMapping(value = "getHistoricalRecordByUser.do")
    @ResponseBody
    public ServerResponse<List<HistoricalRecord>> getHistoryRecordByUser (HttpSession session){
        //先从session中获取用户
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorMessage("请您先登录，再查看历史记录！");
        } else {
            String phoneNumber = user.getPhone();
            return iVideoService.getHistoricalRecordByUser(phoneNumber);
        }
    }
    @RequestMapping(value = "addHistoricalRecordByUser.do")
    @ResponseBody
    public ServerResponse<List<HistoricalRecord>> addHistoryRecordByUser (HttpSession session,
                                                                          HistoricalRecord historicalRecord){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorMessage("未登录用户，不能添加历史记录");
        } else {
            String phoneNumber = user.getPhone();
            return iVideoService.addHistoricalRecordByUser(phoneNumber,historicalRecord);
        }
    }
    @RequestMapping(value = "deleteHistoricalRecordByUser.do")
    @ResponseBody
    public  ServerResponse<List<HistoricalRecord>> deleteHistoryByUser(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null ) {
            return ServerResponse.createByErrorMessage("未登录，不能进行删除操作");
        } else {
            String phoneNumber = user.getPhone();
            return iVideoService.deleteHistoricalRecordByUser(phoneNumber);
        }

    }
}
