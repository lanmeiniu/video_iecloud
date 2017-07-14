package com.iecloud.dao;

import com.iecloud.pojo.Video;

import java.util.List;

public interface VideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> selectMatchKeyWord(String keyWord);

    int checkVideoTitle(String videoTitle);
}