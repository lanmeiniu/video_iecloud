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

    ServerResponse<List<HistoricalRecord>> getHistoricalRecordByUser(String phoneNumber);

    ServerResponse<List<HistoricalRecord>> addHistoricalRecordByUser(String phoneNumber,HistoricalRecord historicalRecord);

    ServerResponse<List<HistoricalRecord>> deleteHistoricalRecordByUser (String phoneNumber);
}
