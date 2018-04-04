package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.UserService;
import cn.edu.uestc.cms.entity.UserBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.concurrent.Callable;

/**
 * 外部接口api
 *
 * @author zhangjj
 * @create 2017-08-24 11:35
 **/
@Controller
@RequestMapping("api")
@Validated
public class APIController {

    private Logger logger = LoggerFactory.getLogger(APIController.class);

    @Resource(name = "userService")
    private UserService userService;

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


    @RequestMapping(value="/asny")
    @ResponseBody
    public WebAsyncTask<String> asny(){
        logger.info("/login被调用 thread id is : " + Thread.currentThread().getName());
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(5000); //模拟长时间任务
                ModelAndView mav = new ModelAndView("login/index");
                logger.info("执行成功 thread id is : " + Thread.currentThread().getName());
                return "异步返回";
            }
        };
        return new WebAsyncTask<>(callable);
    }

    @RequestMapping(value="/user/info/{id}")
    @ResponseBody
    public UserBean userInfo(@Min(1)@PathVariable Integer id){
        UserBean user = userService.selectByPrimaryKey(id);
        return user;
    }
}



















