package cn.edu.uestc.cms;

import cn.edu.uestc.cms.entity.UserBean;
import cn.edu.uestc.cms.page.Page;

public interface UserService {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBean record);

    int insertSelective(UserBean record);

    UserBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBean record);

    int updateByPrimaryKey(UserBean record);

    void listByPage(Page<UserBean> page);
}