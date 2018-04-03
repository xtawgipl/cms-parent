package cn.edu.uestc.cms.configuration.global;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 通过@ControllerAdvice，我们可以将对于控制器的全局配置放置在同一个位置，
 * 注解了@ControllerAdvice的类的方法可使用@ExceptionHandler，@InitBinder，
 * @ModelAttribute注解到方法上，这对所有注解了@RequestMapping的控制器内的方法有效。
 *
 * @author zhangjj
 * @create 2018-04-03 14:17
 **/
@ControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     *
     * @ExceptionHandler：用于全局处理控制器的异常
     *
     * @param
     * @author zhangjj
     * @Date 2018/4/3 14:21
     * @return
     * @exception
     *
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");//error页面
        modelAndView.addObject("errorMessage",exception.getMessage());
        return modelAndView;
    }

}
