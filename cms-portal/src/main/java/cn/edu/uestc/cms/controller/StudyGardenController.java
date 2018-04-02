package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.StudyGardenBean;
import cn.edu.uestc.cms.party.build.IStudyGardenService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.page.Page;
import org.apache.commons.lang3.time.DateFormatUtils;
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
 * 学习园地controller
 *
 * @author zhangjj
 * @create 2017-08-21 12:08
 **/
@Controller
@RequestMapping("study/manager")
public class StudyGardenController {

    private Logger logger = LoggerFactory.getLogger(TalkNotesManagerController.class);

    @Resource
    private Constant constant;

    @Resource(name="studyGardenService")
    private IStudyGardenService studyGardenService;

    /**
     * @description 学习园地  页面
     * @param
     * @author zhangjj
     * @Date 2017/8/18 17:30
     * @return
     * @exception
     */
    @GetMapping("/studyGardenListView")
    public String studyGardenListView(ModelAndView model) {

        return "studyGardenListView";
    }


    /**
     * @description 学习园地 数据
     * @param pageSize 每页多少条数据
     *       pageIndex 请求第几页
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/studyGardenList")
    @ResponseBody
    public String studyGardenList(Page<StudyGardenBean> page) {
        logger.info("page = " + JSONObject.toJSONString(page));
        studyGardenService.studyGardenList(page);
        List<StudyGardenBean> results = page.getResults();
        JSONObject data = new JSONObject();
        data.put("total", page.getTotalSize());
        JSONArray rows = new JSONArray();
        for(StudyGardenBean studyGardenBean : results){
            rows.add(studyGardenBean);
        }
        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * @description 删除 学习园地
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/delStudyGarden")
    @ResponseBody
    public String delStudyGarden(long id) {
        logger.info("id = " + id);
        int rows = studyGardenService.delStudyGarden(id);
        if(rows > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }

    /**
     * @description 添加 学习园地
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/addStudyGarden")
    @ResponseBody
    public String addStudyGarden(StudyGardenBean studyGardenBean) {
        logger.info("studyGardenBean = " + JSONObject.toJSONString(studyGardenBean));
        studyGardenBean.setDateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Long id = studyGardenService.addStudyGarden(studyGardenBean);
        if(id > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }
}
