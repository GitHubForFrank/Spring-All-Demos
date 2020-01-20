package com.zmz.core.api.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author ASNPHDG
 * @create 2020-01-04 19:46
 */
public class BaseAction extends ActionSupport{

    public static final String LIST = "list";
    public static final String TO_LIST = "toList";
    public static final String INPUT = "input";

    /**
     * 页码值
     */
    public Integer pageNum = 1;
    /**
     * 每页显示数据总量
     */
    public Integer pageCount = 10;
    /**
     * 最大页码值
     */
    public Integer maxPageNum ;
    /**
     * 数据总量
     */
    public Integer dataTotal;

    private String actionName;

    /**
     * 根据当前执行的Action类获取Action类的名称中的局部字符串
     * @return
     */
    public String getActionName() {
        String actionName =getClass().getSimpleName();
        String subName = actionName.substring(0,actionName.length()-6);
        return subName.substring(0,1).toLowerCase()+subName.substring(1);
    }

    protected void setDataTotal(Integer dataTotal){
        this.dataTotal = dataTotal;
        maxPageNum = (dataTotal + pageCount -1) / pageCount;
    }

    protected void put(String name,Object obj){
        ActionContext.getContext().put(name, obj);
    }

    protected void putSession(String name,Object obj){
        ActionContext.getContext().getSession().put(name, obj);
    }

//	protected EmpModel getLogin(){
//		return (EmpModel) ActionContext.getContext().getSession().get("loginEm");
//	}

    protected HttpServletRequest getRequest(){
        return ServletActionContext.getRequest();
    }

    protected HttpServletResponse getResponse(){
        return ServletActionContext.getResponse();
    }

}

