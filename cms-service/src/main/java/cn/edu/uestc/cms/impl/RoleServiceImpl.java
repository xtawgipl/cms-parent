package cn.edu.uestc.cms.impl;

import cn.edu.uestc.cms.RoleService;
import cn.edu.uestc.cms.entity.RoleBean;
import cn.edu.uestc.cms.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

    @Resource
    private RoleMapper roleMapper;

    public int deleteByPrimaryKey(Integer id){
        return roleMapper.deleteByPrimaryKey(id);
    }
    public int insert(RoleBean record){
        return roleMapper.insert(record);
    }

    public int insertSelective(RoleBean record){
        return roleMapper.insertSelective(record);
    }

    public RoleBean selectByPrimaryKey(Integer id){
        return roleMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(RoleBean record){
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(RoleBean record){
        return roleMapper.updateByPrimaryKey(record);
    }
}