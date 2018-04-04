package cn.edu.uestc.cms.impl;

import cn.edu.uestc.cms.UserService;
import cn.edu.uestc.cms.entity.UserBean;
import cn.edu.uestc.cms.mapper.UserMapper;
import cn.edu.uestc.cms.page.Page;
import cn.edu.uestc.cms.page.annotion.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
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

    @Transactional
    public UserBean selectByPrimaryKey(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(UserBean record){
        return userMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserBean record){
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    @Pageable
    public void listByPage(Page<UserBean> page) {
        userMapper.listByPage(page);
    }
}