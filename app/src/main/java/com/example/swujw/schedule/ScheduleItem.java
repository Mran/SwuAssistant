package com.example.swujw.schedule;

/**
 * Created by 张孟尧 on 2016/3/4.
 */
public class ScheduleItem
{
    /*教室*/
    private String cdmc;
    /*上课节带节*/
    private String jc;
    /*上课节不带节*/
    private String jcor;
    /*课程名称*/
    private String kcmc;
    /*任课老师姓名*/
    private String xm;
    /*星期*/
    private String xqjmc;
    /*上课地区*/
    private String xqmc;
    /*上课周*/
    private String zcd;
    /*星期几*/
    private int xqj;
    /*课时*/
    private int classCount;
    /*起始节*/
    private int start;
    /*结束节*/
    private int end;
    /**/
    private String textShow;

    public String getTextShow()
    {
        return textShow;
    }

    public void setTextShow(String textShow)
    {
        this.textShow = textShow;
    }

    public String getKcmc()
    {
        return kcmc;
    }

    public int getEnd()
    {
        return end;
    }

    public int getXqj()
    {
        return xqj;
    }

    public int getStart()
    {
        return start;
    }

    public String getCdmc()
    {
        return cdmc;
    }

    public int getClassCount()
    {
        return classCount;
    }

    public void setClassCount(int classCount)
    {
        this.classCount = classCount;
    }

    public String getJc()
    {
        return jc;
    }

    public String getJcor()
    {
        return jcor;
    }


    public String getXm()
    {
        return xm;
    }

    public String getXqjmc()
    {
        return xqjmc;
    }

    public String getXqmc()
    {
        return xqmc;
    }

    public String getZcd()
    {
        return zcd;
    }

    public void setCdmc(String cdmc)
    {
        this.cdmc = cdmc;
    }

    public void setJc(String jc)
    {
        this.jc = jc;
    }

    public void setJcor(String jcor)
    {
        this.jcor = jcor;
    }

    public void setXqj(int xqj)
    {
        this.xqj = xqj;
    }

    public void setEnd(int end)
    {
        this.end = end;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public void setKcmc(String kcmc)
    {
        this.kcmc = kcmc;
    }

    public void setXm(String xm)
    {
        this.xm = xm;
    }

    public void setXqjmc(String xqjmc)
    {
        this.xqjmc = xqjmc;
    }

    public void setXqmc(String xqmc)
    {
        this.xqmc = xqmc;
    }

    public void setZcd(String zcd)
    {
        this.zcd = zcd;
    }
}
