package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.UserService;
import cn.edu.uestc.cms.base.ResultBean;
import cn.edu.uestc.cms.entity.UserBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static List<Map<String, String>> goods = new ArrayList<>();

    static{

        for(int i = 1; i < 12; ++i){
            Map<String, String> map = new HashMap<>();
            map.put("goodId", String.valueOf(i));
            map.put("goodName", "衣服_上衣" + "_" + String.valueOf(i));
            goods.add(map);
        }

        for(int i = 1; i < 5; ++i){
            Map<String, String> map = new HashMap<>();
            map.put("goodId", String.valueOf(i));
            map.put("goodName", "衣服_外套" + "_" + String.valueOf(i));
            goods.add(map);
        }


        for(int i = 1; i < 5; ++i){
            Map<String, String> map = new HashMap<>();
            map.put("goodId", String.valueOf(i));
            map.put("goodName", "裤子_短的" + "_" + String.valueOf(i));
            goods.add(map);
        }

        for(int i = 1; i < 11; ++i){
            Map<String, String> map = new HashMap<>();
            map.put("goodId", String.valueOf(i));
            map.put("goodName", "裤子_长裤" + "_" + String.valueOf(i));
            goods.add(map);
        }
    }

    private boolean exist(Integer goodId){
        for(Map<String, String> map : goods){
            if(map.get("goodId").equals(goodId)){
                return true;
            }
        }
        return false;
    }

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

    @RequestMapping(value="/user/info", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public UserBean userInfo(Integer id){
        UserBean user = userService.selectByPrimaryKey(id);
        return user;
    }

    @RequestMapping(value="/login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultBean login(HttpServletRequest request,
                            @NotNull@NotBlank@Length(max = 20, message = "用户名最多20个字节") String userName,
                            @NotNull@NotBlank@Length(min = 10, message = "密码最少要10个字节") String password){
        ResultBean resultBean;
        if("liuqun".equals(userName) && "123456789abcd".equals(password)){
            resultBean = new ResultBean("0000", true, "登录成功!", null);
            request.getSession().setAttribute("loginState", "1");
        }else{
            resultBean = new ResultBean("0001", false, "登录失败，用户名名密码错误!", null);
        }
        return resultBean;
    }

    @RequestMapping(value="/logout", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultBean logout(HttpServletRequest request){
        ResultBean resultBean = new ResultBean("0009", true, "注销成功!", null);
        request.getSession().invalidate();
        return resultBean;
    }


    @RequestMapping(value="/order", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultBean order(HttpServletRequest request, @Min(value = 1, message = "商品id必须为大于0的整数") Integer goodId){
        ResultBean resultBean;
        Object loginState = request.getSession().getAttribute("loginState");
        if(loginState == null){
            resultBean = new ResultBean("0002", false, "购买失败,未登录!", null);
        }else{
            if(!loginState.toString().equals("1")){
                resultBean = new ResultBean("0002", false, "购买失败,未登录，请先登录！", null);
            }else if(exist(goodId)){
                resultBean = new ResultBean("0007", false, "下单失败，商品id不存在!", null);
            }else{
                resultBean = new ResultBean("0003", true, "下单成功!", null);
            }
        }
        return resultBean;
    }

    @RequestMapping(value="/listGoods", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultBean listGoods(HttpServletRequest request, @Length(max = 20, message = "搜索关键字最多20字节") String keyword){
        List<Map<String, String>> goodList = new ArrayList<>();
        if(StringUtils.isEmpty(keyword)){
            goodList = goods.subList(0, 10);
        }else{
            for(Map<String, String> map : goods){
                if(map.get("goodName").contains(keyword)){
                    goodList.add(map);
                }
            }
        }
        ResultBean resultBean = new ResultBean("0004", true, "查询成功!", goodList);

        return resultBean;
    }


    @RequestMapping(value="/info", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultBean info(HttpServletRequest request, @Min(value = 1, message = "商品id必须为大于0的整数") Integer goodId){
        List<Map<String, String>> goods = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("goodId", String.valueOf(goodId));
        map.put("goodName", "HIZZ邹邹小个子 2018春季新款仙气网纱半身裙 不规则显瘦a字裙" + "_" + String.valueOf(goodId));
        goods.add(map);
        ResultBean resultBean;
        if(exist(goodId)){
            resultBean = new ResultBean("0007", false, "下单失败，商品id不存在!", null);
        }else {
            resultBean = new ResultBean("0004", true, "查询成功!", goods);
        }

        return resultBean;
    }
}



















