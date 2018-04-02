package cn.edu.uestc.cms.mapper;

import cn.edu.uestc.cms.entity.RoleBean;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleBean record);

    int insertSelective(RoleBean record);

    RoleBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleBean record);

    int updateByPrimaryKey(RoleBean record);
}