package com.swuos.ALLFragment.swujw;

import com.swuos.ALLFragment.swujw.grade.util.GradesData;
import com.swuos.ALLFragment.swujw.schedule.util.ScheduleData;
import com.swuos.ALLFragment.swujw.schedule.util.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
public class TotalInfo {
    //    姓名
    private static String name = null;
    //    学号
    private static String swuID = null;
    /*用户名*/
    private static String userName = null;
    /*密码*/
    private static String password = null;
    /*课程表*/
    private static ScheduleData scheduleData = null;
    /*课表表源json数据*/
    private static String scheduleDataJson = null;
    /*保存课程表的列表*/
    private static List<ScheduleItem> scheduleItemList = new ArrayList<>();
    private static int position;
    /*成绩*/
    private static GradesData gradesData;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        TotalInfo.position = position;
    }

    public void setName(String name) {
        TotalInfo.name = name;
    }

    public void setSwuID(String swuID) {
        TotalInfo.swuID = swuID;
    }

    public String getScheduleDataJson() {
        return scheduleDataJson;
    }

    public void setScheduleDataJson(String scheduleDataJson) {
        TotalInfo.scheduleDataJson = scheduleDataJson;
    }

    public List<ScheduleItem> getScheduleItemList() {
        return scheduleItemList;
    }

    public void setScheduleItemList(List<ScheduleItem> scheduleItemList) {
        TotalInfo.scheduleItemList = scheduleItemList;
    }

    public String getName() {
        return name;
    }

    public String getSwuID() {
        return swuID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        TotalInfo.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        TotalInfo.userName = userName;
    }

    public void setGrades(GradesData gradesData) {
        TotalInfo.gradesData = gradesData;
    }

    public GradesData getGrades() {
        return gradesData;
    }

    public ScheduleData getScheduleData() {
        return scheduleData;
    }

    public void setScheduleData(ScheduleData scheduleData) {
        TotalInfo.scheduleData = scheduleData;
    }
}
