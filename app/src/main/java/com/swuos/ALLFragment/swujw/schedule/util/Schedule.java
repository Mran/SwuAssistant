package com.swuos.ALLFragment.swujw.schedule.util;

import com.google.gson.Gson;
import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by 张孟尧 on 2016/3/4.
 */
public class Schedule {

    private OkhttpNet okhttpNet;

    public Schedule(OkhttpNet okhttpNet) {

        this.okhttpNet = okhttpNet;
        /*进入教务系统*/
        okhttpNet.doGet(Constant.urlEms);
    }

    public String setSchedule(TotalInfo totalInfo, String xnm, String xqm) {

        RequestBody requestBody = new FormBody.Builder()
                .add("xnm", xnm)
                .add("xqm", xqm)
                .build();
         /*构建目标网址*/
        String url = "http://jw.swu.edu.cn/jwglxt/kbcx/xskbcx_cxXsKb.html?" + "gnmkdmKey=N253508&sessionUserKey=" + totalInfo.getSwuID();
        /*发送请求*/
        String respones = okhttpNet.doPost(url, requestBody);
        if (!respones.contains(Constant.NO_NET) && respones.contains("kcmc")) {
            totalInfo.setScheduleDataJson(respones);
        } else
            return Constant.CLIENT_ERROR;
        return Constant.CLIENT_OK;
    }

    public static List<ScheduleItem> getScheduleList(TotalInfo totalInfo) {
        /*储存课程表的列表*/
        List<ScheduleItem> scheduleItemList = new ArrayList<>();
        /*处理后的课程表的列表,直接用*/
        List<ScheduleItem> scheduleItemListSort = new ArrayList<>();
         /*构建gson数据来解析json数据*/
        Gson gson = new Gson();
        totalInfo.setScheduleData(gson.fromJson(totalInfo.getScheduleDataJson(), ScheduleData.class));
        ScheduleData scheduleData = totalInfo.getScheduleData();
        ScheduleData.KbList kbList;
        for (int i = 0; i < scheduleData.getKbList().size(); i++) {

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
            /*起始上课节*/
            scheduleItem.setStart(Integer.valueOf(temp[0]));
            /*结束上课节*/
            scheduleItem.setEnd(Integer.valueOf(temp[1]));
            /*节数*/
            scheduleItem.setClassCount(scheduleItem.getEnd() - scheduleItem.getStart() + 1);
            /*星期几*/
            scheduleItem.setXqj(Integer.valueOf(kbList.getXqj()));

            scheduleItem.setTextShow(scheduleItem.getKcmc() + "\n" + scheduleItem.getCdmc() + "\n" + scheduleItem.getJc() + "\n");
            scheduleItem.setTextShowAll(scheduleItem.getKcmc() + "\n" + scheduleItem.getCdmc() + "\n" + scheduleItem.getJc() + "\n" + scheduleItem.getZcd());
            scheduleItem.setClassweek(week(scheduleItem.getZcd()));
            //            scheduleItem.setClassStartTime(Constant.STARTtIMEHOUR[scheduleItem.getStart()], Constant.STARTtIMEMIN[scheduleItem.getStart()]);
            scheduleItem.setStartTime(Constant.STARTtIMES[scheduleItem.getStart() - 1] * 1000 * 60 + (scheduleItem.getXqj() - 1) * Constant.ONE_DAY_TIME);
            int pos = 1;
            /*判断该课程已经存在*/
            for (int j = 0; j < scheduleItemListSort.size(); j++) {
                ScheduleItem tempSchedule = scheduleItemListSort.get(j);

                if (tempSchedule.getKcmc().equals(scheduleItem.getKcmc()) && tempSchedule.getXqjmc().equals(scheduleItem.getXqjmc()) && tempSchedule.getJc().equals(scheduleItem.getJc())) {
                    scheduleItemListSort.get(j).setZcd(scheduleItemListSort.get(j).getZcd() + "," + scheduleItem.getZcd());
                    scheduleItemListSort.get(j).setClassweek(week(scheduleItemListSort.get(j).getZcd()));
                    /*总课表显示内容加上周*/
                    scheduleItemListSort.get(j).setTextShowAll(tempSchedule.getTextShow() + scheduleItem.getZcd());
                    pos = 0;
                    break;
                }
            }
            /*pos为1说明是新添加scheduleitem*/
            if (pos == 1)

                scheduleItemListSort.add(scheduleItem);
            else
                pos = 1;
        }
        return scheduleItemListSort;
    }

    /*用于处理上课周*/
    private static Boolean[] week(String zcd) {

        Boolean[] classWeek = new Boolean[21];
        classWeek[0] = true;
        /*初始化为false*/
        for (int i = 1; i < 21; i++) {
            classWeek[i] = false;
        }
        /*现将"周"字去掉*/
        zcd = zcd.replace("周", "");
        /*以逗号为标记,进行分片*/
        String zcds[] = zcd.split(",");
        for (String aaa : zcds) {
            /*如果包含"-"说明该数据的格式为"1-10"或"1-10(单/双)"这种格式的*/
            if (aaa.contains("-")) {
                /*如果有"双"*/
                if (aaa.contains("双")) {
                    /*去掉"双"字*/
                    aaa = aaa.replace("(双)", "");
                    /*以"-"为标记分片*/
                    String zzzzss[] = aaa.split("-");
                    int j = Integer.valueOf(zzzzss[1]);
                    for (int i = Integer.valueOf(zzzzss[0]); i <= j; i++) {
                        /*上双周课的情况处理*/
                        if (i % 2 == 0)
                            classWeek[i] = true;
                    }
                } else if (aaa.contains("单")) {
                    aaa = aaa.replace("(单)", "");
                    String zzzzss[] = aaa.split("-");
                    int j = Integer.valueOf(zzzzss[1]);
                    for (int i = Integer.valueOf(zzzzss[0]); i <= j; i++) {
                        if (i % 2 != 0)
                            classWeek[i] = true;
                    }
                } else {
                    String zzzzss[] = aaa.split("-");
                    int j = Integer.valueOf(zzzzss[1]);
                    for (int i = Integer.valueOf(zzzzss[0]); i <= j; i++) {
                        classWeek[i] = true;
                    }
                }
            } else {
                classWeek[Integer.valueOf(aaa)] = true;
            }
        }
        return classWeek;
    }
}
