package com.swuos.ALLFragment.swujw.grade.util;
/*用于listview显示*/
public class GradeItem
{
    /*课程名称*/
    private String kcmc;
    /*成绩*/
    private String cj;
    /*绩点*/
    private String jd;
    /*学分*/
    private String xf;
public String ss;
    public void setKcmc(String kcmc)
    {
        this.kcmc = kcmc;
    }
    public String getKcmc()
    {
        return kcmc;
    }

    public String getCj()
    {
        return cj;
    }

    public void setCj(String cj)
    {
        this.cj = cj;
    }

    public String getJd()
    {
        return jd;
    }

    public void setJd(String jd)
    {
        this.jd = jd;
    }

    public String getXf()
    {
        return xf;
    }

    public void setXf(String xf)
    {
        this.xf = xf;
    }
}
