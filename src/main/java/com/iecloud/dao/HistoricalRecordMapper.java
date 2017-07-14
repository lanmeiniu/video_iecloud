package com.iecloud.dao;

import com.iecloud.pojo.HistoricalRecord;

import java.util.List;

public interface HistoricalRecordMapper {
    int deleteByPrimaryKey(String phoneNumber);

    int insert(HistoricalRecord record);

    int insertSelective(HistoricalRecord record);

    HistoricalRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HistoricalRecord record);

    int updateByPrimaryKey(HistoricalRecord record);

    List<HistoricalRecord> selectAllFromHistory();

    List<HistoricalRecord> getHistoryByUser(String phoneNumber);

    int checkPhone (String phoneNumber);

    int updateByPhoneNumber (HistoricalRecord record);
}