package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.UserInfoBean;
import cn.edu.uestc.cms.page.Page;
import cn.edu.uestc.cms.party.build.IUserInfoService;
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
import java.util.Date;
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

    @Resource(name="userInfoService")
    private IUserInfoService userInfoService;

    @GetMapping("/")
    public String main(ModelAndView model) {
        model.addObject("time", new Date());
        model.addObject("message", "2222222222");
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
    public String userInfoList(Page<UserInfoBean> page) {
        logger.info("page = " + JSONObject.toJSONString(page));
        JSONObject data = new JSONObject();
        data.put("total", 7);
        String[] reals = new String[]{"习大大","刘三","张三","李四","王五","小六","李三"};
        String[] logins = new String[]{"wangsi","刀剑如梦","梦林","依一","依晨","庶庶","顶替"};
        JSONArray rows = new JSONArray();
        for(int i = 0; i < 7; ++i){
            JSONObject obj = new JSONObject();
            obj.put("realName", reals[i]);
            obj.put("loginName", logins[i]);
            rows.add(obj);
        }

        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }


}
