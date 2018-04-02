package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.*;
import cn.edu.uestc.cms.page.Page;
import cn.edu.uestc.cms.party.build.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外部接口api
 *
 * @author zhangjj
 * @create 2017-08-24 11:35
 **/
@Controller
@RequestMapping("api")
public class APIController {

    private Logger logger = LoggerFactory.getLogger(APIController.class);

    @Resource(name="partyKpiStatisService")
    private IPartyKpiStatisService partyKpiStatisService;

    @Resource(name="talkNotesService")
    private ITalkNotesService talkNotesService;

    @Resource(name="partyCultureService")
    private IPartyCultureService partyCultureService;

    @Resource(name="partyFeeService")
    private IPartyFeeService partyFeeService;

    @Resource(name="dailyLearningService")
    private IDailyLearningService dailyLearningService;

    @Resource(name="specialLearningService")
    private ISpecialLearningService specialLearningService;

    @Resource(name = "newService")
    private INewService newService;

    @Resource(name="partyModelService")
    private IPartyModelService partyModelService;

    @Resource(name="studyGardenService")
    private IStudyGardenService studyGardenService;

    @Resource
    private Constant constant;

    @ApiOperation(value="用户岗位列表", notes="该用户在某月份中的岗位列表(某些警员可能在一个月内执行了两个月或以上岗位)————岗位类型(1铁骑、2夜巡 、3整治、4事故、5内勤)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "policemanId", value = "用户(党员——警员表id主键值)", required = true),
            @ApiImplicitParam(paramType = "query", name = "month", value = "月份", required = true)
    })
    @GetMapping("/station/list")
    @ResponseBody
    public String stationList(Long policemanId, String month) {
        List<PartyKpiStatisBean> stationList = partyKpiStatisService.stationList(policemanId, month);
        ResultBean resultBean = ResultBean.success();
        List<Map<String, Object>> list = new ArrayList<>();
        for(PartyKpiStatisBean station : stationList){
            Map<String, Object> map = new HashMap<>();
            map.put("station", station.getStationType());
            map.put("text", getStation(station.getStationType()));
            list.add(map);
        }
        resultBean.setRows(list);
        return resultBean.toJsonString();
    }

    private String getStation(Integer stationType){
        switch (stationType){
            case 1:
                return "铁骑岗";
            case 2:
                return "夜巡岗";
            case 3:
                return "整治岗";
            case 4:
                return "事故岗";
            case 5:
                return "内勤岗";
        }
        return null;
    }


    @ApiOperation(value="各项指标在全支队排名", notes="各项指标在全支队排名")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "policemanId", value = "用户(党员——警员表id主键值)", required = true),
            @ApiImplicitParam(paramType = "query", name = "month", value = "月份", required = true),
            @ApiImplicitParam(paramType = "query", name = "stationType", value = "岗位类型(1铁骑、2夜巡 、3整治、4事故、5内勤)", required = true)
    })
    @GetMapping("/kpi/top/single")
    @ResponseBody
    public String kpiTopSingle(Long policemanId, String month, Integer stationType) {
        List<PartyKpiStatisBean> topDigit = partyKpiStatisService.kpiTopSingle(policemanId, month, stationType);
        ResultBean resultBean = ResultBean.success();
        resultBean.setRows(topDigit);
        return resultBean.toJsonString();
    }

    @ApiOperation(value="中队排名", notes="某月份中某岗位在中队排名列表,只返回 前十名,自身，最后一名 三者的并集")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "policemanId", value = "用户(党员——警员表id主键值)", required = true),
            @ApiImplicitParam(paramType = "query", name = "month", value = "月份", required = true),
            @ApiImplicitParam(paramType = "query", name = "stationType", value = "岗位类型(1铁骑、2夜巡 、3整治、4事故、5内勤)", required = true)
    })
    @GetMapping("/kpi/top/squadron/list")
    @ResponseBody
    public String kpiTopSquadronList(Long policemanId, String month, Integer stationType) {
        List<PartyKpiStatisBean> topList = partyKpiStatisService.kpiTopSquadronList(policemanId, month, stationType);
        ResultBean resultBean = ResultBean.success();
        resultBean.setRows(topList);
        return resultBean.toJsonString();
    }


    @ApiOperation(value="大队排名", notes="某月份中某岗位在大队（大队及以下的中队之和）排名列表,只返回 前十名,自身，最后一名 三者的并集")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "policemanId", value = "用户(党员——警员表id主键值)", required = true),
            @ApiImplicitParam(paramType = "query", name = "month", value = "月份", required = true),
            @ApiImplicitParam(paramType = "query", name = "stationType", value = "岗位类型(1铁骑、2夜巡 、3整治、4事故、5内勤)", required = true)
    })
    @GetMapping("/kpi/top/crops/list")
    @ResponseBody
    public String kpiTopCropsList(Long policemanId, String month, Integer stationType) {
        List<PartyKpiStatisBean> topList = partyKpiStatisService.kpiTopCropsList(policemanId, month, stationType);
        ResultBean resultBean = ResultBean.success();
        resultBean.setRows(topList);
        return resultBean.toJsonString();
    }


    @ApiOperation(value="支队排名", notes="某月份中某岗位在支队（全休交警党员）排名列表,只返回 前十名,自身，最后一名 三者的并集")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "policemanId", value = "用户(党员——警员表id主键值)", required = true),
            @ApiImplicitParam(paramType = "query", name = "month", value = "月份", required = true),
            @ApiImplicitParam(paramType = "query", name = "stationType", value = "岗位类型(1铁骑、2夜巡 、3整治、4事故、5内勤)", required = true)
    })
    @GetMapping("/kpi/top/division/list")
    @ResponseBody
    public String kpiTopDivisionList(Long policemanId, String month, Integer stationType) {
        List<PartyKpiStatisBean> topList = partyKpiStatisService.kpiTopDivisionList(policemanId, month, stationType);
        ResultBean resultBean = ResultBean.success();
        resultBean.setRows(topList);
        return resultBean.toJsonString();
    }


    @ApiOperation(value="谈话记录列表", notes="分页 谈话记录列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页大小", required = true),
            @ApiImplicitParam(paramType = "query", name = "flag", value = "0 正常谈话 1异常谈话", required = true),
            @ApiImplicitParam(paramType = "query", name = "policemanId", value = "被谈话对象：用户(党员——警员表id主键值)", required = true)
    })
    @GetMapping("/talkNotesList")
    @ResponseBody
    public String talkNotesList(Integer pageNo, Integer pageSize, Long policemanId, Integer flag) {
        Page<TalkNotesBean> page = new Page<TalkNotesBean>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        logger.info("page = " + JSONObject.toJSONString(page));
        talkNotesService.talkNotesList(page, policemanId, flag);
        List<TalkNotesBean> results = page.getResults();
        logger.info("results-- > " + JSONObject.toJSONString(results, SerializerFeature.DisableCircularReferenceDetect));
        JSONObject data = new JSONObject();
        data.put("total", page.getTotalSize());
        JSONArray rows = new JSONArray();
        for(TalkNotesBean talkNotesBean : results){
            rows.add(talkNotesBean);
        }
        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }


    @ApiOperation(value="党组织生活列表", notes="分页 党组织生活列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/partyCultureList")
    @ResponseBody
    public String partyCultureList(Integer pageNo, Integer pageSize) {
        Page<PartyCultureBean> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        logger.info("page = " + JSONObject.toJSONString(page));
        partyCultureService.partyCultureList(page);
        List<PartyCultureBean> results = page.getResults();
        JSONObject data = new JSONObject();
        data.put("total", page.getTotalSize());
        JSONArray rows = new JSONArray();
        for(PartyCultureBean partyCultureBean : results){
            rows.add(partyCultureBean);
        }
        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }

    @ApiOperation(value="党费缴纳情况 列表", notes="分页 党费缴纳情况列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页大小", required = true),
            @ApiImplicitParam(paramType = "query", name = "policemanId", value = "党费缴纳人：用户(党员——警员表id主键值)", required = true)
    })
    @GetMapping("/partyFeeList")
    @ResponseBody
    public String partyFeeList(Integer pageNo, Integer pageSize, Long policemanId) {
        Page<PartyFeeBean> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        logger.info("page = " + JSONObject.toJSONString(page));
        partyFeeService.partyFeeList(page, policemanId);
        List<PartyFeeBean> results = page.getResults();
        logger.info("results-- > " + JSONObject.toJSONString(results, SerializerFeature.DisableCircularReferenceDetect));
        JSONObject data = new JSONObject();
        data.put("total", page.getTotalSize());
        JSONArray rows = new JSONArray();
        for(PartyFeeBean partyFeeBean : results){
            rows.add(partyFeeBean);
        }
        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }


    @ApiOperation(value="日常学习 列表", notes="分页 日常学习列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页大小", required = true),
            @ApiImplicitParam(paramType = "query", name = "policemanId", value = "日常学习人：用户(党员——警员表id主键值)", required = true)
    })
    @GetMapping("/dailyLearningList")
    @ResponseBody
    public String dailyLearningList(Integer pageNo, Integer pageSize, Long policemanId) {
        Page<DailyLearningBean> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        logger.info("page = " + JSONObject.toJSONString(page));
        dailyLearningService.dailyLearningList(page, policemanId);
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

    @ApiOperation(value="专项学习 列表", notes="分页 专项学习列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页大小", required = true),
            @ApiImplicitParam(paramType = "query", name = "policemanId", value = "专项学习人：用户(党员——警员表id主键值)", required = true)
    })
    @GetMapping("/specialLearningList")
    @ResponseBody
    public String specialLearningList(Integer pageNo, Integer pageSize, Long policemanId) {
        Page<SpecialLearningBean> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        logger.info("page = " + JSONObject.toJSONString(page));
        specialLearningService.specialLearningList(page, policemanId);
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

    @ApiOperation(value="时政要闻 列表", notes="分页 时政要闻列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/newList")
    @ResponseBody
    public String newList(Integer pageNo, Integer pageSize, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

        Page<NewBean> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        logger.info("page = " + JSONObject.toJSONString(page));
        newService.newList(page);
        List<NewBean> results = page.getResults();
        JSONObject data = new JSONObject();
        data.put("total", page.getTotalSize());
        JSONArray rows = new JSONArray();
        for(NewBean newBean : results){
            rows.add(newBean);
        }
        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }

    @ApiOperation(value="党员先锋模范 列表", notes="分页 党员先锋模范列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/partyModelList")
    @ResponseBody
    public String partyModelList(Integer pageNo, Integer pageSize) {
        Page<PartyModelBean> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        logger.info("page = " + JSONObject.toJSONString(page));
        partyModelService.partyModelList(page);
        List<PartyModelBean> results = page.getResults();
        logger.info("results-- > " + JSONObject.toJSONString(results, SerializerFeature.DisableCircularReferenceDetect));
        JSONObject data = new JSONObject();
        data.put("total", page.getTotalSize());
        JSONArray rows = new JSONArray();
        for(PartyModelBean partyModelBean : results){
            rows.add(partyModelBean);
        }
        data.put("rows", rows);
        logger.info("" + JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        return JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
    }


    @ApiOperation(value="学习园地 列表", notes="分页 学习园地列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/studyGardenList")
    @ResponseBody
    public String studyGardenList(Integer pageNo, Integer pageSize) {
        Page<StudyGardenBean> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
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
}



















