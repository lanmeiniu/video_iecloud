package com.iecloud.service;

import com.iecloud.common.ServerResponse;
import com.iecloud.pojo.HistoricalRecord;
import com.iecloud.pojo.Video;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
public interface IVideoService {
    ServerResponse<List<Video>> searchVideo(String keyWord);

    ServerResponse<String> insertVideoRecord(Video video);

    ServerResponse<String> updateRecord(Video video);

    ServerResponse<String> deleteVideoRecord(String id);

    ServerResponse<List<HistoricalRecord>> getHistoricalRecord();

    ServerResponse<List<HistoricalRecord>> getHistoryByUser(String phoneNumber);

    ServerResponse<List<HistoricalRecord>> addHistoryByUser(String phoneNumber,HistoricalRecord historicalRecord);

    ServerResponse<List<HistoricalRecord>> deleteHistoryByUser (String phoneNumber,HistoricalRecord historicalRecord);
}
