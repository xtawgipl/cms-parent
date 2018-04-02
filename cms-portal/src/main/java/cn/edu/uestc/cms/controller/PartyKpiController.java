package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.party.build.IPartyKpiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 绩效考核数据controller
 *
 * @author zhangjj
 * @create 2017-08-23 17:16
 **/
@Controller
@RequestMapping("partyKPI")
public class PartyKpiController {

    private Logger logger = LoggerFactory.getLogger(PartyKpiController.class);

    @Resource
    private Constant constant;

    @Resource(name="partyKpiService")
    private IPartyKpiService partyKpiService;



    /**
     * @description 上传考核数据
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    /*@PostMapping("/uploadKPIData")
    @ResponseBody
    public String uploadKPIData(@RequestParam("kpiFile") MultipartFile kpiFile) {
        if (kpiFile != null && !kpiFile.isEmpty()) {
            try {
                Long id = partyKpiService.uploadKPIData(kpiFile.getInputStream());
                if(id > 0){
                    return ResultBean.success().toJsonString();
                }else{
                    return ResultBean.fail().toJsonString();
                }
            } catch (Exception e) {
                logger.error("", e);
                return ResultBean.fail().toJsonString();
            }
        } else {
            return ResultBean.fail().toJsonString();
        }
    }*/

    @GetMapping("/uploadKPIData/test")
    @ResponseBody
    public String uploadKPIData() throws IOException {

//        FileInputStream fileInputStream = new FileInputStream(new File("D:\\project\\partyBuild\\party-build-portal\\src\\main\\resources\\data\\station\\6月份勤务平台小铁骑勤务数据统计.xlsx"));
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\project\\partyBuild\\party-build-portal\\src\\main\\resources\\data\\station\\7月份勤务平台夜间勤务数据统计.xlsx"));

        partyKpiService.uploadKPIData(fileInputStream, 2, 2);
        return ResultBean.success().toJsonString();
    }
}
