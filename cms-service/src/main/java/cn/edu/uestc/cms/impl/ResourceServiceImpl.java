package cn.edu.uestc.cms.impl;

import cn.edu.uestc.cms.ResourceService;
import cn.edu.uestc.cms.entity.ResourceBean;
import cn.edu.uestc.cms.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService{

    @Resource
    private ResourceMapper resourceMapper;

    public int deleteByPrimaryKey(Integer id){
        return resourceMapper.deleteByPrimaryKey(id);
    }

    public int insert(ResourceBean record){
        return resourceMapper.insert(record);
    }

    public int insertSelective(ResourceBean record){
        return resourceMapper.insertSelective(record);
    }

    public ResourceBean selectByPrimaryKey(Integer id){
        return resourceMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ResourceBean record){
        return resourceMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ResourceBean record){
        return resourceMapper.updateByPrimaryKey(record);
    }
}