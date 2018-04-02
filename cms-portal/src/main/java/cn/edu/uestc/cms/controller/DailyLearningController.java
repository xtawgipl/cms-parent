package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.party.build.IDailyLearningService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.edu.uestc.cms.entity.DailyLearningBean;
import cn.edu.uestc.cms.page.Page;
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
 * 日常学习controller
 *
 * @author zhangjj
 * @create 2017-08-22 14:24
 **/
@Controller
@RequestMapping("dailyLearning/manager")
public class DailyLearningController {

    private Logger logger = LoggerFactory.getLogger(DailyLearningController.class);

    @Resource(name="dailyLearningService")
    private IDailyLearningService dailyLearningService;

    @Resource
    private Constant constant;

    /**
     * @description 日常学习 列表  页面
     * @param
     * @author zhangjj
     * @Date 2017/8/18 17:30
     * @return
     * @exception
     */
    @GetMapping("/dailyLearningListView")
    public String dailyLearningListView(ModelAndView model) {

        return "dailyLearningListView";
    }


    /**
     * @description 日常学习列表 数据 pageSize 每页多少条数据
     *       pageIndex 请求第几页
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/dailyLearningList")
    @ResponseBody
    public String dailyLearningList(Page<DailyLearningBean> page) {
        logger.info("page = " + JSONObject.toJSONString(page));
        dailyLearningService.dailyLearningList(page, null);
        List<DailyLearningBean> results = page.getResults();
        logger.info("results-- > " + JSONObject.toJSONString(results, SerializerFeature.DisableCircularReferenceDetect));
        JSONObject data = new JSONObject();
        data.put("total", page.getTotalSize());
        JSONArray rows = new JSONArray();
        for(DailyLearningBean dailyLearningBean : results){
            rows.add(dailyLearningBean);
        }
        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * @description 删除 日常学习
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/delDailyLearning")
    @ResponseBody
    public String delDailyLearning(long id) {
        logger.info("id = " + id);
        int rows = dailyLearningService.delDailyLearning(id);
        if(rows > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }

    /**
     * @description 添加 日常学习
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/addDailyLearning")
    @ResponseBody
    public String addDailyLearning(DailyLearningBean dailyLearningBean) {
        logger.info("dailyLearningBean = " + JSONObject.toJSONString(dailyLearningBean));
        Long id = dailyLearningService.addDailyLearning(dailyLearningBean);
        if(id > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }
}
