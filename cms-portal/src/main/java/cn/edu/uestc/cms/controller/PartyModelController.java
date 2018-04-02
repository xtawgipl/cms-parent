package cn.edu.uestc.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.PartyModelBean;
import cn.edu.uestc.cms.page.Page;
import cn.edu.uestc.cms.party.build.IPartyModelService;
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
 * 党员先锋模范controller
 *
 * @author zhangjj
 * @create 2017-08-21 14:29
 **/
@Controller
@RequestMapping("partyModel/manager")
public class PartyModelController {

    private Logger logger = LoggerFactory.getLogger(PartyModelController.class);

    @Resource
    private Constant constant;

    @Resource(name="partyModelService")
    private IPartyModelService partyModelService;

    /**
     * @description 党员先锋模范  页面
     * @param
     * @author zhangjj
     * @Date 2017/8/18 17:30
     * @return
     * @exception
     */
    @GetMapping("/partyModelListView")
    public String partyModelListView(ModelAndView model) {
        logger.info("--goto partyModelListView111");
        return "partyModelListView";
    }


    /**
     * @description 党员先锋模范 数据
     * @param pageSize 每页多少条数据
     *       pageIndex 请求第几页
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/partyModelList")
    @ResponseBody
    public String partyModelList(Page<PartyModelBean> page) {
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

    /**
     * @description 删除 党员先锋模范
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/delPartyModel")
    @ResponseBody
    public String delPartyModel(long id) {
        logger.info("id = " + id);
        int rows = partyModelService.delPartyModel(id);
        if(rows > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }

    /**
     * @description 添加 党员先锋模范
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/addPartyModel")
    @ResponseBody
    public String addPartyModel(@RequestParam("photoFile") MultipartFile photoFile, PartyModelBean partyModelBean) {
        logger.info("partyModelBean = " + JSONObject.toJSONString(partyModelBean));
        if (photoFile != null && !photoFile.isEmpty()) {
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(constant.getPhotoPathPrefix() + constant.getPhoto().getPartyModel() + photoFile.getOriginalFilename())));
                out.write(photoFile.getBytes());
                out.flush();
                partyModelBean.setPhoto(constant.getPhotoUrlPrefix() + constant.getPhoto().getPartyModel() + photoFile.getOriginalFilename());
                Long id = partyModelService.addPartyModel(partyModelBean);
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
