package com.qk.oauth.dao.mapper;

import com.qk.oauth.model.po.ClientDetails;

public interface ClientDetailsMapper {
    int deleteByPrimaryKey(String appid);

    int insert(ClientDetails record);

    int insertSelective(ClientDetails record);

    ClientDetails selectByPrimaryKey(String appid);

    int updateByPrimaryKeySelective(ClientDetails record);

    int updateByPrimaryKey(ClientDetails record);
}