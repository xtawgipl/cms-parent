package cn.edu.uestc.cms;

import cn.edu.uestc.cms.entity.RoleResourceRefBean;

public interface RoleResourceRefBeanService {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleResourceRefBean record);

    int insertSelective(RoleResourceRefBean record);

    RoleResourceRefBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleResourceRefBean record);

    int updateByPrimaryKey(RoleResourceRefBean record);
}