package com.example.tool;

import android.util.Log;

import com.example.swuassistant.Constant;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张孟尧 on 2016/1/6.
 */
public class AllData
{
    private static String getBasicInfo(TotalInfo totalInfo, Client client)
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
//            System.out.println("学号" + totalInfo.getSwuID());
        } else return response1;
        return Constant.CLIENT_OK;
    }

    private static String getGrades(TotalInfo totalInfo, Client client)
    {
        /*构建一个post的参数*/
        List<NameValuePair> postNameValuePairs = new ArrayList<>();
        postNameValuePairs.add(new BasicNameValuePair("_search", "false"));
        postNameValuePairs.add(new BasicNameValuePair("nd", "1451922678091"));
        postNameValuePairs.add(new BasicNameValuePair("queryModel.currentPage", "1"));
        postNameValuePairs.add(new BasicNameValuePair("queryModel.showCount", "30"));
        postNameValuePairs.add(new BasicNameValuePair("queryModel.sortName", ""));
        postNameValuePairs.add(new BasicNameValuePair("queryModel.sortOrder", "asc"));
        postNameValuePairs.add(new BasicNameValuePair("time", "0"));
        postNameValuePairs.add(new BasicNameValuePair("xnm", "2015"));
        postNameValuePairs.add(new BasicNameValuePair("xqm", "3"));
        /*构建目标网址*/
        String url = "http://jw.swu.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?" + "doType=query&gnmkdmKey=N305005&sessionUserKey=" + totalInfo.getSwuID();
        /*发送请求*/
        String respones = client.doPost(url, postNameValuePairs);
        if (!respones.contains(Constant.NO_NET))
        {
            /*因为获得数据前面有一个"null"所以对获得的内容进行整理*/
            respones = respones.substring(4);
            /*构建gson数据来解析json数据*/
            Gson gson = new Gson();
            totalInfo.setGrades(gson.fromJson(respones, GradesData.class));
        } else return respones;
        return Constant.CLIENT_OK;
    }

    public  String setBasicInfo(TotalInfo totalInfo, Client client)
    {
        /*对基本信息进行设置*/
        return getBasicInfo(totalInfo, client);
    }

    public  String setGrades(TotalInfo totalInfo, Client client)
    {
        /*对成绩信息进行设置*/
        return getGrades(totalInfo, client);
    }

}

