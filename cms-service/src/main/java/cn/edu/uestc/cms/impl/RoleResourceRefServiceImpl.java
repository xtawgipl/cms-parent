package cn.edu.uestc.cms.impl;

import cn.edu.uestc.cms.RoleResourceRefBeanService;
import cn.edu.uestc.cms.entity.RoleResourceRefBean;
import cn.edu.uestc.cms.mapper.RoleResourceRefMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("roleResourceRefBeanService")
public class RoleResourceRefServiceImpl implements RoleResourceRefBeanService{

    @Resource
    private RoleResourceRefMapper roleResourceRefMapper;

    public int deleteByPrimaryKey(Integer id){
        return roleResourceRefMapper.deleteByPrimaryKey(id);
    }

    public int insert(RoleResourceRefBean record){
        return roleResourceRefMapper.insert(record);
    }

    public int insertSelective(RoleResourceRefBean record){
        return roleResourceRefMapper.insertSelective(record);
    }

    public RoleResourceRefBean selectByPrimaryKey(Integer id){
        return roleResourceRefMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(RoleResourceRefBean record){
        return roleResourceRefMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(RoleResourceRefBean record){
        return roleResourceRefMapper.updateByPrimaryKey(record);
    }
}