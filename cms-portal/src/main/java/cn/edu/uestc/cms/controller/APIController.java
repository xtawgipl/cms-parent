package cn.edu.uestc.cms.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ApiOperation(value="测试 列表", notes="分页 测试")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "第几页", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/list")
    @ResponseBody
    public String list(Integer pageNo, Integer pageSize) {
        return "";
    }
}



















