package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.PartyCultureBean;
import cn.edu.uestc.cms.party.build.IPartyCultureService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.edu.uestc.cms.page.Page;
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
 * 党组织生活controller
 *
 * @author zhangjj
 * @create 2017-08-22 11:56
 **/
@Controller
@RequestMapping("partyCulture/manager")
public class PartyCultureController {

    private Logger logger = LoggerFactory.getLogger(PartyCultureController.class);

    @Resource
    private Constant constant;

    @Resource(name="partyCultureService")
    private IPartyCultureService partyCultureService;

    /**
     * @description 党组织生活 列表  页面
     * @param
     * @author zhangjj
     * @Date 2017/8/18 17:30
     * @return
     * @exception
     */
    @GetMapping("/partyCultureView")
    public String partyCultureView(ModelAndView model) {

        return "partyCultureView";
    }


    /**
     * @description 党组织生活 列表 数据
     * @param pageSize 每页多少条数据
     *       pageIndex 请求第几页
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/partyCultureList")
    @ResponseBody
    public String partyCultureList(Page<PartyCultureBean> page) {
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

    /**
     * @description 删除 党组织生活
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/delPartyCulture")
    @ResponseBody
    public String delPartyCulture(long id) {
        logger.info("id = " + id);
        int rows = partyCultureService.delPartyCulture(id);
        if(rows > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }

    /**
     * @description 添加 党组织生活
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/addPartyCulture")
    @ResponseBody
    public String addPartyCulture(@RequestParam("photoFile") MultipartFile photoFile, PartyCultureBean partyCultureBean) {
        logger.info("partyCultureBean = " + JSONObject.toJSONString(partyCultureBean));
        if (photoFile != null && !photoFile.isEmpty()) {
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(constant.getPhotoPathPrefix() + constant.getPhoto().getPartyCulture() + photoFile.getOriginalFilename())));
                out.write(photoFile.getBytes());
                out.flush();
                partyCultureBean.setPhoto(constant.getPhotoUrlPrefix() + constant.getPhoto().getPartyCulture() + photoFile.getOriginalFilename());
                Long id = partyCultureService.addPartyCulture(partyCultureBean);
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
