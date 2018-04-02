package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.UserService;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.UserBean;
import cn.edu.uestc.cms.page.Page;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息controller
 *
 * @author zhangjj
 * @create 2018-03-21 17:28
 **/
@Controller
@RequestMapping("/admin")
public class UserInfoController {

    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Resource
    private Constant constant;

    @Resource(name="userService")
    private UserService userService;

    @GetMapping("/")
    public String main(ModelAndView model) {

        return "main_index";
    }


    /**
     * @description 用户信息  页面
     * @param
     * @author zhangjj
     * @Date 2018-03-21 18:28
     * @return
     * @exception
     */
    @GetMapping("/userInfoListView")
    public String userInfoListView(ModelAndView model) {

        return "userInfoListView";
    }


    /**
     * @description 用户权限信息 列表
     * @param page 每页多少条数据
     *       pageIndex 请求第几页
     * @author zhangjj
     * @Date 2018-03-21 18:28
     * @return
     * @exception
     */
    @PostMapping("/userInfoList")
    @ResponseBody
    public String userInfoList(Page<UserBean> page) {
        logger.info("page = " + JSONObject.toJSONString(page));
        userService.listByPage(page);
        JSONObject data = new JSONObject();
        JSONArray rows = new JSONArray();
        data.put("total", page.getTotalSize());
        List<UserBean> results = page.getResults();
        for(UserBean user : results){
            JSONObject obj = new JSONObject();
            obj.put("realName", user.getRealName());
            obj.put("loginName", user.getLoginName());
            obj.put("id", user.getId());
            obj.put("isAdmin", user.getIsAdmin());
            obj.put("roleId", user.getRoleId());
            rows.add(obj);
        }

        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }


}
