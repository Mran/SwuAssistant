package com.swuos.ALLFragment.swujw;

import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
/*本类实现了三个功能
* 1.登陆教务管理系统
* 2.获取成绩
* 3.获取基本信息(学号、姓名)*/
public class Login {

    /*新建一个连接*/
    public OkhttpNet okhttpNet;
    /*TotalInfo类用来保存用户的基本信息*/
    private static TotalInfo totalInfo = new TotalInfo();

    public Login() {
        okhttpNet = new OkhttpNet();
    }

    /*登录*/
    public String doLogin(String userName, String userPassword) {

/*构建post的参数*/
        RequestBody requestBody = new FormBody.Builder()
                .add("goto", Constant.gotos)
                .add("gotoOnFail", Constant.gotoOnFail)
                .add("Login.Token1", userName)
                .add("Login.Token2", userPassword)
                .build();
        return okhttpNet.doPost(Constant.urlLogin, requestBody);
    }

    public TotalInfo getBasicInfo() {
/*进入教务系统*/
        okhttpNet.doGet(Constant.urlEms);
/*获得基本信息名字和学号*/
        setBasicInfo(totalInfo);
        return totalInfo;
    }

    private String setBasicInfo(TotalInfo totalInfo) {
/*获得学号*/
        String respones = okhttpNet.doGet("http://jw.swu.edu.cn/jwglxt/xtgl/index_initMenu.html", "utf-8");
/*判断是否正确获得结果*/
        if (!respones.contains(Constant.NO_NET)) {
            SALog.d("client", "setBasicInfo() id==>" + respones);
/*对结果进行切割获得学号*/
            String swuidtmple = respones.substring(respones.indexOf("UserKey\" value=\""));
/*将结果保存进totalInfo*/
            totalInfo.setSwuID(swuidtmple.substring(16, 31));
        } else
            return respones;
/*获得姓名*/
        String response1 = okhttpNet.doGet("http://jw.swu.edu.cn/jwglxt/xtgl/index_cxYhxxIndex.html?xt=jw&gnmkdmKey=index&sessionUserKey=" + totalInfo.getSwuID(), "utf-8");

/*判断是否正确获得结果*/
        if (!response1.contains(Constant.NO_NET)) {
            SALog.d("client", "setBasicInfo() name==>" + response1);
/*对结果进行切割获得姓名*/
            String nametmple = response1.substring(response1.indexOf("heading\">"));
/*将结果保存进totalInfo*/
            totalInfo.setName(nametmple.substring(9, nametmple.indexOf("</h4>")));
            SALog.d("client", totalInfo.getName());
        } else
            return response1;
        return Constant.CLIENT_OK;
    }

}

