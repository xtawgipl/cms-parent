package cn.edu.uestc.cms.impl;

import cn.edu.uestc.cms.ContentService;
import cn.edu.uestc.cms.entity.ContentBean;
import cn.edu.uestc.cms.mapper.ContentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("contentService")
public class ContentServiceImpl implements ContentService{

    @Resource
    private ContentMapper contentMapper;

    public int deleteByPrimaryKey(Integer id){
        return contentMapper.deleteByPrimaryKey(id);
    }

    public int insert(ContentBean record){
        return contentMapper.insert(record);
    }

    public int insertSelective(ContentBean record){
        return contentMapper.insertSelective(record);
    }

    public ContentBean selectByPrimaryKey(Integer id){
        return contentMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ContentBean record){
        return contentMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ContentBean record){
        return contentMapper.updateByPrimaryKey(record);
    }
}