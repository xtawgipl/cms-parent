package cn.edu.uestc.cms.base;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 结果bean
 *
 * @author zhangjj
 * @create 2017-08-19 17:43
 **/
public class ResultBean {

    private String code;

    private boolean success;

    private String message;

    private List<?> rows;

    public ResultBean(){

    }

    public ResultBean(String code, boolean success, String message, List<Object> rows) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.rows = rows;
    }

    public static ResultBean success(){
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(true);
        resultBean.setMessage("成功!");
        return resultBean;
    }

    public static ResultBean fail(){
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(false);
        resultBean.setMessage("失败!");
        return resultBean;
    }

    public String toJsonString(){
        return JSONObject.toJSONString(this);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
