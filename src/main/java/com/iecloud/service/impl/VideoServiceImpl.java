package com.iecloud.service.impl;

import com.iecloud.common.ServerResponse;
import com.iecloud.dao.VideoMapper;
import com.iecloud.pojo.Video;
import com.iecloud.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
@Service("iVideoService")
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public ServerResponse<List<Video>> searchVideo(String keyWord) {
        if (org.apache.commons.lang3.StringUtils.isBlank(keyWord)) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccess("获取成功", videoMapper.selectMatchKeyWord(keyWord));
    }

    @Override
    public ServerResponse<String> insertVideoRecord(Video video) {
        if (0 != videoMapper.checkVideoTitle(video.getTitle())) {
            return ServerResponse.createByErrorMessage("已存在同名标题的影片");
        }
        if (0 == videoMapper.insertSelective(video)) {
            return ServerResponse.createByErrorMessage("上传数据失败");
        } else {
            return ServerResponse.createBySuccessMessage("上传数据成功");
        }
    }
}
