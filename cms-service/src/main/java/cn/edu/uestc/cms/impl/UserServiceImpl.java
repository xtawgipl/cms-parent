package cn.edu.uestc.cms.impl;

import cn.edu.uestc.cms.UserService;
import cn.edu.uestc.cms.entity.UserBean;
import cn.edu.uestc.cms.mapper.RoleMapper;
import cn.edu.uestc.cms.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("rserService")
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    public int deleteByPrimaryKey(Integer id){
        return userMapper.deleteByPrimaryKey(id);
    }

    public int insert(UserBean record){
        return userMapper.insert(record);
    }

    public int insertSelective(UserBean record){
        return userMapper.insertSelective(record);
    }

    public UserBean selectByPrimaryKey(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(UserBean record){
        return userMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserBean record){
        return userMapper.updateByPrimaryKey(record);
    }
}