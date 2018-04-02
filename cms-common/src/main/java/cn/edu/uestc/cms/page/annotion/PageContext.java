package cn.edu.uestc.cms.page.annotion;

import cn.edu.uestc.cms.page.Page;

public class PageContext {

	public static final ThreadLocal<Page<Object>> localPage = new ThreadLocal<Page<Object>>(); 
	
	public static void setPage(Page<Object> page){
		localPage.set(page);
	}
	
	public static Page<Object> getPage(){
		return localPage.get();
	}
	
	public static void clear(){
		localPage.remove();
	}
}
