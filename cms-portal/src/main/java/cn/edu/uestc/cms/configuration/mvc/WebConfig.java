package cn.edu.uestc.cms.configuration.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * mvc配置
 *
 * @author zhangjj
 * @create 2018-04-03 14:03
 **/
@Configuration
@EnableWebMvc
@ComponentScan("cn.edu.uestc.cms")
public class WebConfig extends WebMvcConfigurerAdapter{

    private Logger logger = LoggerFactory.getLogger(WebConfig.class);

    /**
     * springmvc 中默认情况下url中的.后面的内容将被忽略，这里可以配置不忽略
     * @param
     * @author zhangjj
     * @Date 2018/4/3 14:37
     * @return
     * @exception
     *
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        super.configureAsyncSupport(configurer);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        super.configureDefaultServletHandling(configurer);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
    }

    /**
     * 拦截器配置
     * 拦截器（Interceptor）实现对每一个请求前后进行相关的业务处理，类似于Servlet的Filter
     *
     * @param
     * @author zhangjj
     * @Date 2018/4/3 14:11
     * @return
     * @exception
     *
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            /**
             * 重写preHandle方法，在请求发生之前执行 
             * @param 
             * @author zhangjj
             * @Date 2018/4/3 14:15
             * @return void
             * @exception
             *
             */
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                logger.info("请求前。。。");
                return true;
            }

            /**
             * 重写postHandle方法，在请求完成之后执行
             * @param
             * @author zhangjj
             * @Date 2018/4/3 14:15
             * @return void
             * @exception 
             * 
             */
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                logger.info("请求后。。。");
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                logger.info("请求完成。。。");
            }
        });
    }


    /**
     * 静态资源映射
     * 程序的静态文件（js，css，图片）等需要直接访问，这时我们可以在配置里重写addResourceHandlers方法来实现
     * @param
     * @author zhangjj
     * @Date 2018/4/3 14:09
     * @return
     * @exception
     *
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceLocations指的是文件放置的目录，addResourceHandler指的是对外暴露的访问路径
        registry.addResourceHandler("/doc/**").addResourceLocations("classpath:/doc/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
    }

    /**
     * 统一处理没啥业务逻辑处理的controller请求，实现代码的简洁
     * 即访问xxx/userInfoListView 则跳转到xxx/userInfoListView.[jsp,html..]
     * @param
     * @author zhangjj
     * @Date 2018/4/3 14:33
     * @return
     * @exception
     *
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/userInfoListView").setViewName("userInfoListView");
    }

    /**
     *
     * 这个方法是用来配置视图解析器的，该方法的参数ViewResolverRegistry 是一个注册器，用来注册你想自定义的视图解析器等
     *
     * @param
     * @author zhangjj
     * @Date 2018/4/3 14:57
     * @return
     * @exception
     *
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        /**
         * 启用内容裁决视图解析器
         */
        registry.enableContentNegotiation(new MappingJackson2JsonView());
//        registry.jsp("/WEB-INF/jsp/", ".jsp");
    }

    /**
     *
     * configureViewResolvers方法中 启用内容裁决解析器，那么configureContentNegotiation(ContentNegotiationConfigurer configurer)
     * 这个方法是专门用来配置内容裁决的一些参数的
     *
     * @param
     * @author zhangjj
     * @Date 2018/4/3 14:55
     * @return
     * @exception
     *
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
                .ignoreAcceptHeader(true)
                .parameterName("mediaType")
                .defaultContentType(MediaType.TEXT_HTML)
                .mediaType("html", MediaType.TEXT_HTML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.extendHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    public Validator getValidator() {
        return super.getValidator();
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return super.getMessageCodesResolver();
    }
}
