package cn.edu.uestc.cms.configuration.global;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 全局 绑定键值对到Model里
 *
 * @author zhangjj
 * @create 2018-04-03 14:25
 **/
@ControllerAdvice
public class ModelHandlerAdvice {

    /**
     *
     * 此处使用@ModelAttribute注解将键值对添加到全局，所有注解了@RequestMapping的方法可获得此键值对
     *
     * @param
     * @author zhangjj
     * @Date 2018/4/3 14:26
     * @return
     * @exception
     *
     */
    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("msg", "额外信息");
    }
}
