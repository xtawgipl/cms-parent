package cn.edu.uestc.cms.controller;

import cn.edu.uestc.cms.constant.Constant;
import cn.edu.uestc.cms.entity.PolicemanBean;
import cn.edu.uestc.cms.party.build.IPolicemanService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Api("警员controller类")
@Controller
@RequestMapping("policeman")
public class PolicemanController {
	
	@Resource(name = "policemanService")
	private IPolicemanService policemanService;

	@Resource
	private Constant constant;

	@ApiIgnore
	@GetMapping("/main")
	public String main(ModelAndView model) {
		model.addObject("time", new Date());
		model.addObject("message", "2222222222");
		return "main_index";
	}
	

//	@ApiOperation(value="查询学生列表", notes="分页查询学生列表")
//	@ApiImplicitParams(value = {
//	        @ApiImplicitParam(paramType = "query", name = "pageNo", value = "请求页码", required = false),
//	        @ApiImplicitParam(paramType = "query", name = "pageSize", value = "请求页大小", required = false)
//	})
//	@GetMapping("/list/page")
//	@ResponseBody
//	public Page<PolicemanBean> listByPage(Page<PolicemanBean> page){
//		policemanService.listByPage(page);
//		return page;
//	}

	@ApiOperation(value="查询学生信息", notes="根据id查询学生信息")
	@ApiImplicitParams(value = {
	        @ApiImplicitParam(paramType = "path", name = "id", value = "唯一标识", required = true)
	})
	@GetMapping("/selectByPrimaryKey/{id}")
	@ResponseBody
	public PolicemanBean selectByPrimaryKey(@PathVariable Long id){
		return policemanService.findByPrimaryKey(id);
	}


	@PostMapping("/dropSelect")
	@ResponseBody
	public String dropSelect(BigDecimal orgId){
//		policemanService.dropSelect(orgId);
		JSONArray data = new JSONArray();
		for(int i = 0; i < 10; ++i){
			JSONObject obj = new JSONObject();
			obj.put("id", i + 1);
			obj.put("text", "姓名 " + i);
			data.add(obj);
		}
		return data.toJSONString();
	}

	
}
