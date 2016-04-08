package com.example.ALLFragment.swujw;

import com.example.ALLFragment.swujw.grade.GradesData;
import com.example.ALLFragment.swujw.schedule.ScheduleData;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
public class TotalInfo {
    //    姓名
    private static String name = null;
    //    学号
    private static String swuID = null;
    /*用户名*/
    private static String userName=null;
    /*密码*/
    private static String password=null;
    /*课程表*/
    private static ScheduleData scheduleData;
    /*课表表源json数据*/
    private static String scheduleDataJson;

    /*成绩*/
    private static GradesData gradesData;

    public void setName(String name) {
        TotalInfo.name = name;
    }

    public void setSwuID(String swuID) {
        TotalInfo.swuID = swuID;
    }

    public static String getScheduleDataJson() {
        return scheduleDataJson;
    }

    public static void setScheduleDataJson(String scheduleDataJson) {
        TotalInfo.scheduleDataJson = scheduleDataJson;
    }

    public String getName() {
        return name;
    }

    public String getSwuID() {
        return swuID;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        TotalInfo.password = password;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
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
