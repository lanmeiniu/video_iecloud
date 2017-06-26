package com.iecloud.controller.portal;

import com.iecloud.common.ServerResponse;
import com.iecloud.pojo.Video;
import com.iecloud.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    ServerResponse<List<Video>> searchVideo(String keyWord) {
        return iVideoService.searchVideo(keyWord);
    }

}
