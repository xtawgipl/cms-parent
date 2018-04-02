package cn.edu.uestc.cms.page.annotion;

import javax.annotation.Resource;

import cn.edu.uestc.cms.page.exception.PageException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import cn.edu.uestc.cms.page.Page;
import cn.edu.uestc.cms.page.starter.PageProterties;

@Configuration
@Aspect
public class PageProcessor {
	
	private Logger logger = LoggerFactory.getLogger(PageProcessor.class);
	
	@Resource(name = "pageProterties")
	private PageProterties pageProterties;
	
	@SuppressWarnings("unchecked")
	@Around("@annotation(cn.edu.uestc.cms.page.annotion.Pageable)")
	public Object processor(ProceedingJoinPoint proceedingJoinPoint){
		Object[] objs = proceedingJoinPoint.getArgs();
		boolean hasPageParam = false;
		Page<Object> page = null;
		for(Object obj : objs){
			if(obj instanceof Page){
				page = (Page<Object>) obj;
				if(page.getPageNo() <= 0){
					if(pageProterties.getDefaultPageNo() > 0){
						page.setPageNo(pageProterties.getDefaultPageNo());
						logger.warn(proceedingJoinPoint.getSignature() + " pageNo is null , set to default : " + pageProterties.getDefaultPageNo());
					}else{
						throw new PageException(proceedingJoinPoint.getSignature() + " pageNo is null!");
					}
				}
				if(page.getPageSize() <= 0){
					if(pageProterties.getDefaultPageSize() > 0){
						page.setPageSize(pageProterties.getDefaultPageSize());
						logger.warn(proceedingJoinPoint.getSignature() + " pageSize is null , set to default : " + pageProterties.getDefaultPageSize());
					}else{
						throw new PageException(proceedingJoinPoint.getSignature() + " pageSize is null!");
					}
				}
				PageContext.setPage(page);
				hasPageParam = true;
				break;
			}
		}
		if(!hasPageParam){
			throw new PageException("方法中没有传入Page参数  : " + proceedingJoinPoint.getSignature());
		}
		try {
			return proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			logger.error("", e);
		}finally {
			PageContext.clear();
		}
		return null;
	}
}
