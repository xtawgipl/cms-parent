package cn.edu.uestc.cms.mapper;

import cn.edu.uestc.cms.entity.MouldBean;

public interface MouldMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MouldBean record);

    int insertSelective(MouldBean record);

    MouldBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MouldBean record);

    int updateByPrimaryKey(MouldBean record);
}