package cn.edu.uestc.cms;

import cn.edu.uestc.cms.entity.ResourceBean;

public interface ResourceService {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceBean record);

    int insertSelective(ResourceBean record);

    ResourceBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResourceBean record);

    int updateByPrimaryKey(ResourceBean record);
}