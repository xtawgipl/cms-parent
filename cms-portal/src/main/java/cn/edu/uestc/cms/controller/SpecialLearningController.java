package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.constant.Constant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.entity.SpecialLearningBean;
import cn.edu.uestc.cms.page.Page;
import cn.edu.uestc.cms.party.build.ISpecialLearningService;
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
 * 专项学习controller
 *
 * @author zhangjj
 * @create 2017-08-22 14:24
 **/
@Controller
@RequestMapping("specialLearning/manager")
public class SpecialLearningController {

    private Logger logger = LoggerFactory.getLogger(SpecialLearningController.class);

    @Resource
    private Constant constant;

    @Resource(name="specialLearningService")
    private ISpecialLearningService specialLearningService;

    /**
     * @description 专项学习 列表  页面
     * @param
     * @author zhangjj
     * @Date 2017/8/18 17:30
     * @return
     * @exception
     */
    @GetMapping("/specialLearningListView")
    public String specialLearningListView(ModelAndView model) {

        return "specialLearningListView";
    }


    /**
     * @description 专项学习列表 数据
     * @param pageSize 每页多少条数据
     *       pageIndex 请求第几页
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/specialLearningList")
    @ResponseBody
    public String specialLearningList(Page<SpecialLearningBean> page) {
        logger.info("page = " + JSONObject.toJSONString(page));
        specialLearningService.specialLearningList(page, null);
        List<SpecialLearningBean> results = page.getResults();
        logger.info("results-- > " + JSONObject.toJSONString(results, SerializerFeature.DisableCircularReferenceDetect));
        JSONObject data = new JSONObject();
        data.put("total", page.getTotalSize());
        JSONArray rows = new JSONArray();
        for(SpecialLearningBean specialLearningBean : results){
            rows.add(specialLearningBean);
        }
        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * @description 删除 专项学习
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/delSpecialLearning")
    @ResponseBody
    public String delSpecialLearning(long id) {
        logger.info("id = " + id);
        int rows = specialLearningService.delSpecialLearning(id);
        if(rows > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }

    /**
     * @description 添加 专项学习
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/addSpecialLearning")
    @ResponseBody
    public String addSpecialLearning(SpecialLearningBean specialLearningBean) {
        logger.info("specialLearningBean = " + JSONObject.toJSONString(specialLearningBean));
        Long id = specialLearningService.addSpecialLearning(specialLearningBean);
        if(id > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }
}
