package com.example.swujw;

import android.util.Log;

import com.example.net.Client;
import com.example.swuassistant.Constant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
/*本类实现了三个功能
* 1.登陆教务管理系统
* 2.获取成绩
* 3.获取基本信息(学号、姓名)*/
public class Login
{
    //    校内门户地址
    private static final String urlUrp = "http://urp6.swu.edu.cn/login.portal";
    //    用户信息发送目标地址
    private static final String urlLogin = "http://urp6.swu.edu.cn/userPasswordValidate.portal";
    //    登陆后跳转网页
    private static final String urlPortal = "http://urp6.swu.edu.cn/index.portal";
    //    #教务系统网站 Ems意为swu Educational management system
    private static final String urlEms = "http://jw.swu.edu.cn/jwglxt/idstar/index.jsp";
    private static final String urlJW = "http://jw.swu.edu.cn/jwglxt/xtgl/index_initMenu.html";
    /*登陆校内门户是post的两个重要参数*/
    private static final String gotos = "http://urp6.swu.edu.cn/loginSuccess.portal";
    private static final String gotoOnFail = "http://urp6.swu.edu.cn/loginFailure.portal";
    /*新建一个连接*/
    public static Client client = new Client();
    /*TotalInfo类用来保存用户的基本信息*/
    private static TotalInfo totalInfo = new TotalInfo();


    /*登录*/
    public String doLogin(String userName, String userPassword)
    {

        /*构建post的参数*/
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("goto", gotos));
        nameValuePairs.add(new BasicNameValuePair("gotoOnFail", gotoOnFail));
        nameValuePairs.add(new BasicNameValuePair("Login.Token1", userName));
        nameValuePairs.add(new BasicNameValuePair("Login.Token2", userPassword));
        return client.doPost(urlLogin, nameValuePairs);
    }

    public  TotalInfo getBasicInfo()
    {
        /*进入教务系统*/
        client.doGet(urlEms);
        /*获得基本信息名字和学号*/
        setBasicInfo(totalInfo, client);
        return totalInfo;
    }
    private  String setBasicInfo(TotalInfo totalInfo, Client client)
    {
        /*获得学号*/
        String respones = client.doGet("http://jw.swu.edu.cn/jwglxt/xtgl/index_initMenu.html");
        /*判断是否正确获得结果*/
        if (!respones.contains(Constant.NO_NET))
        {
            /*对结果进行切割获得学号*/
            String swuidtmple = respones.substring(respones.indexOf("UserKey\" value=\""));
            /*将结果保存进totalInfo*/
            totalInfo.setSwuID(swuidtmple.substring(16, 31));
        } else
            return respones;
        /*获得姓名*/
        String response1 = client.doGet("http://jw.swu.edu.cn/jwglxt/xtgl/index_cxYhxxIndex.html?xt=jw&gnmkdmKey=index&sessionUserKey=" + totalInfo.getSwuID());

        /*判断是否正确获得结果*/
        if (!response1.contains(Constant.NO_NET))
        {
           /*对结果进行切割获得姓名*/
            String nametmple = response1.substring(response1.indexOf("heading\">"));
            /*将结果保存进totalInfo*/
            totalInfo.setName(nametmple.substring(9, nametmple.indexOf("</h4>")));
            Log.d("client", totalInfo.getName());
        } else return response1;
        return Constant.CLIENT_OK;
    }

}

