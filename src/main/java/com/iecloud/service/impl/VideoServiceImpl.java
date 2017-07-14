package com.iecloud.service.impl;

import com.iecloud.common.ServerResponse;
import com.iecloud.dao.HistoricalRecordMapper;
import com.iecloud.dao.UserMapper;
import com.iecloud.dao.VideoMapper;
import com.iecloud.pojo.HistoricalRecord;
import com.iecloud.pojo.Video;
import com.iecloud.service.IUserService;
import com.iecloud.service.IVideoService;
import com.iecloud.util.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
@Service("iVideoService")
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private HistoricalRecordMapper historicalRecordMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService iUserService;


    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @Override
    public ServerResponse<String> updateRecord(Video video){
        int updateCount = videoMapper.updateByPrimaryKeySelective(video);
        if(updateCount > 0) {
            return ServerResponse.createBySuccessMessage("更新成功");
        } else {
            return ServerResponse.createByErrorMessage("没有此视频信息，无法更新");
        }
    }

    @Override
    public ServerResponse<String> deleteVideoRecord(String id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        if(video == null) {
            return ServerResponse.createByErrorMessage("没有视频信息，因此无法进行删除操作");
        } else {
            int deleteCount = videoMapper.deleteByPrimaryKey(id);
            if(deleteCount > 0 ) {
                return ServerResponse.createBySuccessMessage("视频删除成功");

            } else {
                return ServerResponse.createByErrorMessage("视频删除失败，请查明原因");
            }
        }
    }

    @Override
    public ServerResponse<List<HistoricalRecord>> getHistoricalRecord(){
        List<HistoricalRecord> historyList = historicalRecordMapper.selectAllFromHistory();
        if(CollectionUtils.isEmpty(historyList)){
            log.info("没有历史记录");
        }
        return ServerResponse.createBySuccess("ok",historyList);
    }

    @Override
    public ServerResponse<List<HistoricalRecord>> getHistoryByUser(String phoneNumber){
        List<HistoricalRecord> historyList = historicalRecordMapper.getHistoryByUser(phoneNumber);
        if(CollectionUtils.isEmpty(historyList)){
            log.info("当前没有浏览记录");
            return ServerResponse.createByErrorMessage("当前没有历史记录");
        } else {
            return ServerResponse.createBySuccess("ok",historyList);
        }
    }

    @Override
    public ServerResponse<List<HistoricalRecord>> addHistoryByUser(String phoneNumber,
                                                                   HistoricalRecord historicalRecord){
        int resultCount = historicalRecordMapper.checkPhone(phoneNumber);
        if(resultCount > 0 ) {
            //覆盖原来的历史记录
            historicalRecord.setPhoneNumber(phoneNumber);
            int updateCount = historicalRecordMapper.updateByPhoneNumber(historicalRecord);
            if(updateCount > 0 ) {
                return ServerResponse.createBySuccessMessage("此用户记录已经更新成功");
            } else {
                return ServerResponse.createByErrorMessage("更新异常，请查明原因");
            }
        } else {
            //插入用户的历史记录操作
            historicalRecord.setPhoneNumber(phoneNumber);
            int resultCount1 = historicalRecordMapper.insertSelective(historicalRecord);
            if (resultCount1 > 0 ){
                return ServerResponse.createBySuccessMessage("添加浏览记录成功");
            } else {
                return ServerResponse.createByErrorMessage("添加异常，请查明原因");
            }
        }
    }

    @Override
    public ServerResponse<List<HistoricalRecord>> deleteHistoryByUser (String phoneNumber,
                                                                HistoricalRecord historicalRecord){
        int resultCount = historicalRecordMapper.checkPhone(phoneNumber);
        if (resultCount > 0 ) {
            int resultCount1 = historicalRecordMapper.deleteByPrimaryKey(phoneNumber);
            if(resultCount1 > 0){
                return ServerResponse.createBySuccessMessage("已成功删除历史记录");
            } else {
                return ServerResponse.createByErrorMessage("删除失败，请查明原因");
            }
        } else {
            return ServerResponse.createByErrorMessage("该用户无历史播放记录");
        }
    }

}
