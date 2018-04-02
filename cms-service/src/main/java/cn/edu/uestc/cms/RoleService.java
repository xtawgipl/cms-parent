package cn.edu.uestc.cms;

import cn.edu.uestc.cms.entity.RoleBean;

public interface RoleService {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleBean record);

    int insertSelective(RoleBean record);

    RoleBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleBean record);

    int updateByPrimaryKey(RoleBean record);
}