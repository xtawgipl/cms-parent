package cn.edu.uestc.cms.mapper;

import cn.edu.uestc.cms.entity.ResourceBean;

public interface ResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceBean record);

    int insertSelective(ResourceBean record);

    ResourceBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResourceBean record);

    int updateByPrimaryKey(ResourceBean record);
}