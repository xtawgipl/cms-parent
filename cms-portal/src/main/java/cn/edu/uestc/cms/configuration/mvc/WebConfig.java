package cn.edu.uestc.cms.configuration.mvc;

import cn.edu.uestc.cms.entity.UserBean;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.List;

/**
 * mvc配置
 *
 * @author zhangjj
 * @create 2018-04-03 14:03
 **/
@Configuration
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

    /**
     *
     * 在Spring Boot中（Spring MVC）下请求默认都是同步的，一个请求过去到结束都是由一个线程负责的，
     * 很多时候为了能够提高吞吐量，需要将一些操作异步化，除了一些耗时的业务逻辑可以异步化，
     * 我们的查询接口也是可以做到异步执行。
     * http://www.jb51.net/article/134289.htm
     *
     * @param
     * @author zhangjj
     * @Date 2018/4/4 11:17
     * @return
     * @exception
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(60 * 1000L);
        configurer.registerCallableInterceptors(timeoutInterceptor());
        configurer.setTaskExecutor(taskExecutor());
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(1);
        t.setMaxPoolSize(2);
        t.setThreadNamePrefix("asyn");
        return t;
    }

    /**
     * 格式转换器
     * @param
     * @author zhangjj
     * @Date 2018/4/4 14:39
     * @return
     * @exception
     *
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
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

    /**
     * Tomcat通过 org.apache.catalina.servlets.DefaultServlet 来响应所有请求处理
     * Servlet 3.0+以后war中jar文件 META-INF/resources 也是Web应用的根目录。
     * 而：通常Spring工程将所有请求都分发给DispatcherServlet，这样就不会调用Tomcat的DefaultServlet，静态文件也就访问不到了。
     * 继续使用DefaultServlet响应静态文件的话，需要开启请求Forward到DefaultServlet。
     * org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler
     * 或者Spring MVC自己的静态文件处理，即 addResourceHandlers方法
     *
     * @param
     * @author zhangjj
     * @Date 2018/4/4 13:59
     * @return
     * @exception
     *
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
    }

    /**
     * 使用 Cors协议 全局解决跨域问题
     * Cors协议：H5中的新特性：Cross-Origin Resource Sharing（跨域资源共享）
     * 如需要更细粒度可以使用@CrossOrigin
     * @param
     * @author zhangjj
     * @Date 2018/4/4 14:46
     * @return
     * @exception
     *
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST")
                .allowCredentials(false).maxAge(3600);;
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

    /**
     * 参数解析器,url参数自定义方式解析到request
     * @param
     * @author zhangjj
     * @Date 2018/4/4 14:52
     * @return
     * @exception
     *
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        /*argumentResolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameterType().equals(UserBean.class);
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                UserBean user = new UserBean();
                return user;
            }
        });*/
    }

    /**
     *
     *
     *通过@Bean定义HttpMessageConverter是向项目中添加消息转换器最简便的办法，这类似于之前提到的添加Servlet Filters。如果Spring扫描到HttpMessageConverter类型的bean，就会将它自动添加到调用链中。推荐让项目中的WebConfiguration继承自WebMvcConfigurerAdapter
     *
     *通过重写configureMessageConverters方法添加自定义的转换器很方便，但有一个弱点：如果项目中存在多个WebMvcConfigurers的实例（我们自己定义的，或者Spring Boot默认提供的），不能确保重写后的configureMessageConverters方法按照固定顺序执行。
     *
     *如果需要更精细的控制：清除其他消息转换器或者清楚重复的转换器，可以通过重写extendMessageConverters完成，仍然有这种可能：别的WebMvcConfigurer实例也可以重写这个方法，但是这种几率非常小。
     *
     *
     */

    /**
     * controller 方法返回转换
     * @param
     * @author zhangjj
     * @Date 2018/4/4 15:04
     * @return
     * @exception
     *
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }

    /**
     * controller responsebody 返回格式转换，可查看HttpMessageConverter子类
     * @param
     * @author zhangjj
     * @Date 2018/4/4 15:10
     * @return
     * @exception
     *
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
        converters.add(jsonConverter());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter(){
        HttpMessageConverter<String> converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Bean
    public FastJsonHttpMessageConverter4 jsonConverter(){
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();//4
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteClassName,
                SerializerFeature.WriteMapNullValue
        );
        ValueFilter valueFilter = new ValueFilter() {//5
            //o 是class
            //s 是key值
            //o1 是value值
            public Object process(Object o, String s, Object o1) {
                if (null == o1){
                    o1 = "";
                }
                return o1;
            }
        };
        fastJsonConfig.setSerializeFilters(valueFilter);
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }


    /**
     *全局异常处理类
     *
     *
     *
     *
     * @param
     * @author zhangjj
     * @Date 2018/4/4 16:43
     * @return
     * @exception
     *
     */
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
