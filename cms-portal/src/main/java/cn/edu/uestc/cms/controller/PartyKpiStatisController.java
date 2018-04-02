package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.party.build.IPartyKpiStatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 统计
 *
 * @author zhangjj
 * @create 2017-08-24 0:00
 **/
@Controller
@RequestMapping("kpi/statis")
public class PartyKpiStatisController {

    private Logger logger = LoggerFactory.getLogger(PartyKpiStatisController.class);

    @Resource(name="partyKpiStatisService")
    private IPartyKpiStatisService partyKpiStatisService;

    @Resource
    private Constant constant;



    @GetMapping("/sum")
    @ResponseBody
    public String sum() {
        partyKpiStatisService.sum("2017-07");
        return ResultBean.success().toJsonString();
    }

    @GetMapping("/top")
    @ResponseBody
    public String top() {
        partyKpiStatisService.top("2017-07");
        return ResultBean.success().toJsonString();
    }



}
