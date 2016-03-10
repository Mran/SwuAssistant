package com.example.swujw.schedule;

import com.example.net.Client;
import com.example.swuassistant.Constant;
import com.example.swujw.TotalInfo;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/3/4.
 */
public class Schedule
{
    private Client client;

    public Schedule(Client client)
    {
        this.client = client;
        /*进入教务系统*/
        client.doGet(Constant.urlEms);
    }
    public String setSchedule(TotalInfo totalInfo, String xnm, String xqm)
    {
        /*构建一个post的参数*/
        List<NameValuePair> postNameValuePairs = new ArrayList<>();
        /*所查询学年*/
        postNameValuePairs.add(new BasicNameValuePair("xnm", xnm));
        /*所查询学期*/
        postNameValuePairs.add(new BasicNameValuePair("xqm", xqm));
         /*构建目标网址*/
        String url = "http://jw.swu.edu.cn/jwglxt/kbcx/xskbcx_cxXsKb.html?" + "gnmkdmKey=N253508&sessionUserKey=" + totalInfo.getSwuID();
        /*发送请求*/
        String respones = client.doPost(url, postNameValuePairs);
        if (!respones.contains(Constant.NO_NET))
        {

            /*构建gson数据来解析json数据*/
            Gson gson = new Gson();
            totalInfo.setScheduleData(gson.fromJson(respones, ScheduleData.class));
        } else return respones;
        return Constant.CLIENT_OK;
    }

    public List<ScheduleItem> getScheduleList(TotalInfo totalInfo)
    {
        /*储存课程表的列表*/
        List<ScheduleItem> scheduleItemList = new ArrayList<>();
        ScheduleData scheduleData = totalInfo.getScheduleData();
        ScheduleData.KbList kbList;
        for (int i = 0; i < scheduleData.getKbList().size(); i++)
        {
            kbList = scheduleData.getKbList().get(i);
            ScheduleItem scheduleItem = new ScheduleItem();
            scheduleItem.setCdmc(kbList.getCdmc());
            scheduleItem.setXqjmc(kbList.getXqjmc());
            scheduleItem.setKcmc(kbList.getKcmc());
            scheduleItem.setXm(kbList.getXm());
            scheduleItem.setJc(kbList.getJc());
            scheduleItem.setXqmc(kbList.getXqmc());
            scheduleItem.setZcd(kbList.getZcd());
            String temp[] = kbList.getJcor().split("-");
            scheduleItem.setStart(Integer.valueOf(temp[0]));
            scheduleItem.setEnd(Integer.valueOf(temp[1]));
            scheduleItem.setClassCount(scheduleItem.getEnd() - scheduleItem.getStart() + 1);
            scheduleItem.setXqj(Integer.valueOf(kbList.getXqj()));
            scheduleItemList.add(scheduleItem);
        }
        return scheduleItemList;
    }
}
