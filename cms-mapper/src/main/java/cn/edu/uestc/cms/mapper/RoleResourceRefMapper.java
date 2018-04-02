package cn.edu.uestc.cms.mapper;

import cn.edu.uestc.cms.entity.RoleResourceRefBean;

public interface RoleResourceRefMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleResourceRefBean record);

    int insertSelective(RoleResourceRefBean record);

    RoleResourceRefBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleResourceRefBean record);

    int updateByPrimaryKey(RoleResourceRefBean record);
}