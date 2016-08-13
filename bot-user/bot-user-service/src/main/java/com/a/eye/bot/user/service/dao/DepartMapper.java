package com.a.eye.bot.user.service.dao;

import com.a.eye.bot.user.service.entity.Depart;

public interface DepartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Depart record);

    int insertSelective(Depart record);

    Depart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Depart record);

    int updateByPrimaryKey(Depart record);
}