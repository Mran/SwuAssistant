package com.swuos.allfragment.swujw;

import com.swuos.allfragment.swujw.grade.model.GradesData;
import com.swuos.allfragment.swujw.schedule.model.ScheduleData;
import com.swuos.allfragment.swujw.schedule.model.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/7/23.
 */
public class TotalInfos {
    private static TotalInfos ourInstance = new TotalInfos();
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
    private static String gradesDataJson = null;
    /*保存课程表的列表*/
    private static List<ScheduleItem> scheduleItemList = new ArrayList<>();
    private static int position;
    /*成绩*/
    private static GradesData gradesData;

    private TotalInfos() {
    }

    public static String getGradesDataJson() {
        return gradesDataJson;
    }

    public static void setGradesDataJson(String gradesDataJson) {
        TotalInfos.gradesDataJson = gradesDataJson;
    }

    public static GradesData getGradesData() {
        return gradesData;
    }

    public static void setGradesData(GradesData gradesData) {
        TotalInfos.gradesData = gradesData;
    }

    public static TotalInfos getInstance() {
        return ourInstance;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getScheduleDataJson() {
        return scheduleDataJson;
    }

    public void setScheduleDataJson(String scheduleDataJson) {
        this.scheduleDataJson = scheduleDataJson;
    }

    public List<ScheduleItem> getScheduleItemList() {
        return scheduleItemList;
    }

    public void setScheduleItemList(List<ScheduleItem> scheduleItemList) {
        this.scheduleItemList = scheduleItemList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSwuID() {
        return swuID;
    }

    public void setSwuID(String swuID) {
        this.swuID = swuID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GradesData getGrades() {
        return gradesData;
    }

    public void setGrades(GradesData gradesData) {
        this.gradesData = gradesData;
    }

    public ScheduleData getScheduleData() {
        return scheduleData;
    }

    public void setScheduleData(ScheduleData scheduleData) {
        this.scheduleData = scheduleData;
    }

}
