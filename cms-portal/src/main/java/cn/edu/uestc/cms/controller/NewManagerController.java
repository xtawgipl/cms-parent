package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.NewBean;
import cn.edu.uestc.cms.party.build.INewService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.edu.uestc.cms.page.Page;
import org.apache.commons.lang3.time.DateFormatUtils;
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
import java.util.Date;
import java.util.List;

/**
 * 时政要闻controller
 *
 * @author zhangjj
 * @create 2017-08-21 11:36
 **/
@Controller
@RequestMapping("new/manager")
public class NewManagerController {

    private Logger logger = LoggerFactory.getLogger(NewManagerController.class);

    @Resource(name = "newService")
    private INewService newService;

    @Resource
    private Constant constant;

    /**
     * @description 时政要闻列表  页面
     * @param
     * @author zhangjj
     * @Date 2017/8/18 17:30
     * @return
     * @exception
     */
    @GetMapping("/newListView")
    public String newListView(ModelAndView model) {

        return "newListView";
    }


    /**
     * @description 时政要闻列表 数据
     * @param pageSize 每页多少条数据
     *       pageIndex 请求第几页
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/newList")
    @ResponseBody
    public String newList(Page<NewBean> page) {
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

    /**
     * @description 删除 时政要闻
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/delNew")
    @ResponseBody
    public String delNew(long id) {
        logger.info("id = " + id);
        int rows = newService.delNew(id);
        if(rows > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }

    /**
     * @description 添加 时政要闻
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/addNew")
    @ResponseBody
    public String addNew(@RequestParam("photoFile") MultipartFile photoFile, NewBean newBean) {
        logger.info("newBean = " + JSONObject.toJSONString(newBean));
        logger.info("newsPath = " + constant.getPhoto().getNews());
        if (photoFile != null && !photoFile.isEmpty()) {
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(constant.getPhotoPathPrefix() + constant.getPhoto().getNews() + photoFile.getOriginalFilename())));
                out.write(photoFile.getBytes());
                out.flush();
                newBean.setPhoto(constant.getPhotoUrlPrefix() + constant.getPhoto().getNews() + photoFile.getOriginalFilename());
                newBean.setDateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                Long id = newService.addNew(newBean);
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
