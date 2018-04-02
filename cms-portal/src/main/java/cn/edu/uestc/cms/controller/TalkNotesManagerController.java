package cn.edu.uestc.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.TalkNotesBean;
import cn.edu.uestc.cms.page.Page;
import cn.edu.uestc.cms.party.build.ITalkNotesService;
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
 * 谈话记录管理controller
 *
 * @auther create by zhangjj
 * @create 2017/8/17-11:44
 */
@Controller
@RequestMapping("talk/manager")
public class TalkNotesManagerController {

    private Logger logger = LoggerFactory.getLogger(TalkNotesManagerController.class);

    @Resource
    private Constant constant;

    @Resource(name="talkNotesService")
    private ITalkNotesService talkNotesService;

    /**
     * @description 谈话记录列表  页面
     * @param
     * @author zhangjj
     * @Date 2017/8/18 17:30
     * @return
     * @exception
     */
    @GetMapping("/talkNotesListView")
    public String talkNotesListView(ModelAndView model) {

        return "talkNotesListView";
    }


    /**
     * @description 谈话记录列表 数据
    * @param pageSize 每页多少条数据
     *       pageIndex 请求第几页
    * @author zhangjj  
    * @Date 2017/8/19 9:33
    * @return 
    * @exception 
    */
    @PostMapping("/talkNotesList")
    @ResponseBody
    public String talkNotesList(Page<TalkNotesBean> page) {
        logger.info("page = " + JSONObject.toJSONString(page));
        talkNotesService.talkNotesList(page, null, null);
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

    /**
     * @description 删除 谈话记录
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/delTalkNotes")
    @ResponseBody
    public String delTalkNotes(long id) {
        logger.info("id = " + id);
        int rows = talkNotesService.delTalkNotes(id);
        if(rows > 0){
            return ResultBean.success().toJsonString();
        }else{
            return ResultBean.fail().toJsonString();
        }
    }

    /**
     * @description 添加 谈话记录
     * @param
     * @author zhangjj
     * @Date 2017/8/19 9:33
     * @return
     * @exception
     */
    @PostMapping("/addTalkNotes")
    @ResponseBody
    public String addTalkNotes(@RequestParam("photoFile") MultipartFile photoFile, TalkNotesBean talkNotesBean) {
        logger.info("talkNotesBean = " + JSONObject.toJSONString(talkNotesBean));
        if (photoFile != null && !photoFile.isEmpty()) {
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(constant.getPhotoPathPrefix() + constant.getPhoto().getTalkNotes() + photoFile.getOriginalFilename())));
                out.write(photoFile.getBytes());
                out.flush();
                talkNotesBean.setPhoto(constant.getPhotoUrlPrefix() + constant.getPhoto().getTalkNotes() + photoFile.getOriginalFilename());
                Long id = talkNotesService.addTalkNotes(talkNotesBean);
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
