package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.PartyFeeBean;
import cn.edu.uestc.cms.page.Page;
import cn.edu.uestc.cms.party.build.IPartyFeeService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 党费缴纳controller
 *
 * @author zhangjj
 * @create 2017-08-22 13:25
 **/
@Controller
@RequestMapping("partyFee/manager")
public class PartyFeeController {


    private Logger logger = LoggerFactory.getLogger(PartyFeeController.class);

    @Resource
    private Constant constant;

    @Resource(name="partyFeeService")
    private IPartyFeeService partyFeeService;

    /**
     * @description 党费缴纳  页面
     * @param
     * @author zhangjj
     * @Date 2017/8/18 17:30
     * @return
     * @exception
     */
    @GetMapping("/partyFeeListView")
    public String partyFeeListView(ModelAndView model) {
        return "partyFeeListView";
    }


    /**
     * @description 党费缴纳 数据
     * @param page 每页多少条数据
     *       pageIndex 请求第几页
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/partyFeeList")
    @ResponseBody
    public String partyFeeList(Page<PartyFeeBean> page) {
        logger.info("page = " + JSONObject.toJSONString(page));
        partyFeeService.partyFeeList(page, null);
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

    /**
     * @description 删除 党费缴纳
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/delPartyFee")
    @ResponseBody
    public String delPartyFee(long id) {
        logger.info("id = " + id);
        int rows = partyFeeService.delPartyFee(id);
        if(rows > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }

    /**
     * @description 添加 党费缴纳
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/addPartyFee")
    @ResponseBody
    public String addPartyFee(@RequestParam("photoFile") MultipartFile photoFile, PartyFeeBean partyFeeBean) {
        logger.info("partyFeeBean = " + JSONObject.toJSONString(partyFeeBean));
        if (photoFile != null && !photoFile.isEmpty()) {
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(constant.getPhotoPathPrefix() + constant.getPhoto().getPartyFee() + photoFile.getOriginalFilename())));
                out.write(photoFile.getBytes());
                out.flush();
                partyFeeBean.setPhoto(constant.getPhotoUrlPrefix() + constant.getPhoto().getPartyFee() + photoFile.getOriginalFilename());
                Long id = partyFeeService.addPartyFee(partyFeeBean);
                if(id > 0){
                    return ResultBean.success().toJsonString();
                }else{
                    return ResultBean.fail().toJsonString();
                }
            } catch (Exception e) {
                logger.error("", e);
                return ResultBean.fail().toJsonString();
            }finally {
                if(out != null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
            }
        } else {
            return ResultBean.fail().toJsonString();
        }
    }
}
