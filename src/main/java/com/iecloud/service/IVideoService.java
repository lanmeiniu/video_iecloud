package com.iecloud.service;

import com.iecloud.common.ServerResponse;
import com.iecloud.pojo.Video;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
public interface IVideoService {
    ServerResponse<List<Video>> searchVideo(String keyWord);

    ServerResponse<String> insertVideoRecord(Video video);
}
