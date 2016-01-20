package com.example.tool;

import java.util.List;
import java.util.Map;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
public class TotalInfo
{
    //    姓名
    private static String name = null;
    //    学号
    private static String swuID = null;
//    private static Map<String, List<String>> gradesData = null;
    private static Map<String, List<String>> classScheedul = null;
    /*成绩*/
    private static GradesData gradesData;

    public void setName(String name)
    {
        TotalInfo.name = name;
    }

    public void setSwuID(String swuID)
    {
        TotalInfo.swuID = swuID;
    }

    public String getName()
    {
        return name;
    }

    public String getSwuID()
    {
        return swuID;
    }

    public void setGrades(GradesData gradesData)
    {
        TotalInfo.gradesData = gradesData;
    }

    public GradesData getGrades()
    {
        return gradesData;
    }
}
