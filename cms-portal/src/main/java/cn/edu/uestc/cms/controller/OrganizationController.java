package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.constant.Constant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 部门controller
 *
 * @author zhangjj
 * @create 2017-08-21 22:21
 **/
@Controller
@RequestMapping("org")
public class OrganizationController {

    private Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Resource
    private Constant constant;

    /**
     * @description 部门树
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/orgTree")
    @ResponseBody
    public String orgTree() {
        JSONArray data = new JSONArray();
        JSONObject note = new JSONObject();
        note.put("id", "1");
        note.put("text", "交警支队");
        JSONArray notes = new JSONArray();
        for(int i = 0; i < 10; ++i){
            JSONObject child = new JSONObject();
            child.put("id", i + 2);
            child.put("text", "大队" + i);
            notes.add(child);
        }
        note.put("nodes", notes);
        data.add(note);
        logger.info("orgTree --> " + data.toJSONString());
        return data.toJSONString();
    }
}
