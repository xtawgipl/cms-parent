package cn.edu.uestc.cms.mapper;

import cn.edu.uestc.cms.entity.ContentBean;

public interface ContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContentBean record);

    int insertSelective(ContentBean record);

    ContentBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContentBean record);

    int updateByPrimaryKey(ContentBean record);
}