package com.swuos.ALLFragment.swujw;

import android.util.Base64;

import com.google.gson.Gson;
import com.swuos.net.OkhttpNet;
import com.swuos.net.jsona.LoginJson;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
/*本类实现了三个功能
* 1.登陆教务管理系统
* 2.获取成绩
* 3.获取基本信息(学号、姓名)*/
public class Login {

    /*TotalInfo类用来保存用户的基本信息*/
    private static TotalInfos totalInfo =TotalInfos.getInstance();
    /*新建一个连接*/
    public OkhttpNet okhttpNet;

    public Login() {
        okhttpNet = new OkhttpNet();
    }

    /*登录*/
    public LoginJson doLogin(String userName, String userPassword) {
        String swuLoginjsons = "{\"serviceAddress\":\"https://uaaap.swu.edu.cn/cas/ws/acpInfoManagerWS\",\"serviceType\":\"soap\",\"serviceSource\":\"td\",\"paramDataFormat\":\"xml\",\"httpMethod\":\"POST\",\"soapInterface\":\"getUserInfoByUserName\",\"params\":{\"userName\":\"" + userName + "\",\"passwd\":\"" + userPassword + "\",\"clientId\":\"yzsfwmh\",\"clientSecret\":\"1qazz@WSX3edc$RFV\",\"url\":\"http://i.swu.edu.cn\"},\"cDataPath\":[],\"namespace\":\"\",\"xml_json\":\"\"}";

/*构建post的参数*/
        RequestBody requestBody = new FormBody.Builder()
                .add("serviceInfo", swuLoginjsons)
                .build();

        String response = okhttpNet.doPost(Constant.urlLogin, requestBody);
        Gson gson = new Gson();
        return gson.fromJson(response, LoginJson.class);
    }

    public TotalInfos getBasicInfo(LoginJson loginJson) {
        String tgt = loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getAttributes().getTgt();
        String cookie = String.format("CASTGC=\"%s\"; rtx_rep=no", new String(Base64.decode(tgt, Base64.DEFAULT)));
        Cookie cookies = Cookie.parse(HttpUrl.parse("http://jw.swu.edu.cn"), cookie);
/*进入教务系统*/
        okhttpNet.doGet(Constant.urlRedrictjw, cookies);
/*获得基本信息名字和学号*/
        //        setBasicInfo(totalInfo);
        LoginJson.DataBean.GetUserInfoByUserNameResponseBean.ReturnBean.InfoBean.AttributesBean returnBean = loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getAttributes();
        totalInfo.setName(new String(Base64.decode(returnBean.getACPNAME(), Base64.DEFAULT)));
        totalInfo.setSwuID(loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getId());
        totalInfo.setUserName(new String(Base64.decode(returnBean.getACPNAME(), Base64.DEFAULT)));
        return totalInfo;
    }

    //    private String setBasicInfo(TotalInfos totalInfo) {
    ///*获得学号*/
    //        String respones = okhttpNet.doGet("http://jw.swu.edu.cn/jwglxt/xtgl/index_initMenu.html", "utf-8");
    ///*判断是否正确获得结果*/
    //        if (!respones.contains(Constant.NO_NET)) {
    //            SALog.d("client", "setBasicInfo() id==>" + respones);
    ///*对结果进行切割获得学号*/
    //            String swuidtmple = respones.substring(respones.indexOf("UserKey\" value=\""));
    ///*将结果保存进totalInfo*/
    //            totalInfo.setSwuID(swuidtmple.substring(16, 31));
    //        } else
    //            return respones;
    ///*获得姓名*/
    //        String response1 = okhttpNet.doGet("http://jw.swu.edu.cn/jwglxt/xtgl/index_cxYhxxIndex.html?xt=jw&gnmkdmKey=index&sessionUserKey=" + totalInfo.getSwuID(), "utf-8");
    //
    ///*判断是否正确获得结果*/
    //        if (!response1.contains(Constant.NO_NET)) {
    //            SALog.d("client", "setBasicInfo() name==>" + response1);
    ///*对结果进行切割获得姓名*/
    //            String nametmple = response1.substring(response1.indexOf("heading\">"));
    ///*将结果保存进totalInfo*/
    //            totalInfo.setName(nametmple.substring(9, nametmple.indexOf("</h4>")));
    //            SALog.d("client", totalInfo.getName());
    //        } else
    //            return response1;
    //        return Constant.CLIENT_OK;
    //    }

}

