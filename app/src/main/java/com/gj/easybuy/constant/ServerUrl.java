package com.gj.easybuy.constant;


/**
 * 接口
 *
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/10/18
 * @Describe
 */
public class ServerUrl {


    //----------业务模块接口根路径------------

    /**
     * 工作台接口根路径
     */
    private static final String API_ROOT = "/api";


    /**************************************通用接口*******************************************/
    /**
     * 登录接口
     */
    public static final String POST_LOGIN = API_ROOT + "/Login/Login";







    /**************************************首页*******************************************/



    /**************************************购物车*******************************************/




    /**************************************我*******************************************/

    /**
     * 拼接访问接口
     *
     * @param url 接口
     * @param url 接口
     * @return
     */
    public final static String getApiUrl(String url) {
        return RootUrl.HN_HOST + url;
    }

}
