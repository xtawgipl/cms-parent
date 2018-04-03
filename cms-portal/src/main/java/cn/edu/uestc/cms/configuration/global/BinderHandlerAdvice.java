package cn.edu.uestc.cms.configuration.global;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 全局 绑定
 * 用来设置WebDataBinder，WebDataBinder用来自动绑定前台请求参数到Model中。
 *
 * @author zhangjj
 * @create 2018-04-03 14:28
 **/
@ControllerAdvice
public class BinderHandlerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        //此处演示忽略request参数的id
        webDataBinder.setDisallowedFields("id");
    }
}
