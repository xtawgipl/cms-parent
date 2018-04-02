package cn.edu.uestc.cms;

import cn.edu.uestc.cms.entity.ContentBean;

public interface ContentService {
    int deleteByPrimaryKey(Integer id);

    int insert(ContentBean record);

    int insertSelective(ContentBean record);

    ContentBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContentBean record);

    int updateByPrimaryKey(ContentBean record);
}