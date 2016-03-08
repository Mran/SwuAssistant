package com.example.swuassistant;

/**
 * Created by 张孟尧 on 2016/1/10.
 */
public class Constant
{
    public static final int TIMEOUT = 4000;
    //    设置Message标记
    public static final int SHOW_RESPONSE = 0;
    public static final int ERROR = 1;
    public static final int DELETE = 2;

    public static final int LOGIN_SUCCESE = 3;
    public static final int LOGIN_FAILED = 4;
    public static final int LOGIN_TIMEOUT = 10;
    public static final int LOGIN_CLIENT_ERROR = 11;
    public static final int LOGIN_NO_NET = 12;


    public static final int GRADES_OK = 5;
    public static final int UPDATA = 6;
    public static final int MAIN = 7;
    public static final int SHOW = 8;
    public static final int DISSHOW = 9;
    public static final int SCHEDULE_OK = 13;

    public static final String NO_NET = "网络出现了问题";
    public static final String CLIENT_OK = "成功";
    public static final String CLIENT_ERROR = "连接出错";
    public static final String CLIENT_TIMEOUT = "连接超时";

    public static final String XQM_ONE = "3";
    public static final String XQM_TWO = "12";
    public static final String XQM_THREE = "16";
    public static final String XQM_ALL = "";
    public static final String[] ALL_XQM = {XQM_ALL, XQM_ONE, XQM_TWO, XQM_THREE};
    public static final String[] ALL_XNM = {"", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010"};
    public static final String NET_TIMEOUT = "NET_TIMEOUT";
    public static final String[] FRAGMENTTAG = {"mainPageFragment", "scheduleFragment", "gradesFragment", "studyMaterialsFragment", "findLostFragment", "chargeFragment", "libraryFragrment"};
    //    校内门户地址
    public static final String urlUrp = "http://urp6.swu.edu.cn/login.portal";
    //    用户信息发送目标地址
    public static final String urlLogin = "http://urp6.swu.edu.cn/userPasswordValidate.portal";
    //    登陆后跳转网页
    public static final String urlPortal = "http://urp6.swu.edu.cn/index.portal";
    //    #教务系统网站 Ems意为swu Educational management system
    public static final String urlEms = "http://jw.swu.edu.cn/jwglxt/idstar/index.jsp";
    public static final String urlJW = "http://jw.swu.edu.cn/jwglxt/xtgl/index_initMenu.html";
    /*登陆校内门户是post的两个重要参数*/
    public static final String gotos = "http://urp6.swu.edu.cn/loginSuccess.portal";
    public static final String gotoOnFail = "http://urp6.swu.edu.cn/loginFailure.portal";
}

