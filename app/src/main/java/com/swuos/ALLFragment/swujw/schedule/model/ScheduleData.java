package com.swuos.allfragment.swujw.schedule.model;

import android.widget.TextView;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/3/3.
 */
public class ScheduleData
{
    private List<KbList> kbList;

    public List<KbList> getKbList() {
        return kbList;
    }

    public void setKbList(List<KbList> kbList) {
        this.kbList = kbList;
    }

    public static class KbList
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
        /*上课周数字*/
        private String xqj;
        public String getKcmc()
        {
            return kcmc;
        }

        public void setKcmc(String kcmc)
        {
            this.kcmc = kcmc;
        }

        public String getCdmc()
        {
            return cdmc;
        }

        public void setCdmc(String cdmc)
        {
            this.cdmc = cdmc;
        }

        public String getJc()
        {
            return jc;
        }

        public void setJc(String jc)
        {
            this.jc = jc;
        }

        public String getJcor()
        {
            return jcor;
        }

        public void setJcor(String jcor)
        {
            this.jcor = jcor;
        }

        public String getXm()
        {
            return xm;
        }

        public void setXm(String xm)
        {
            this.xm = xm;
        }

        public String getXqjmc()
        {
            return xqjmc;
        }

        public void setXqjmc(String xqjmc)
        {
            this.xqjmc = xqjmc;
        }

        public String getXqmc()
        {
            return xqmc;
        }

        public void setXqmc(String xqmc)
        {
            this.xqmc = xqmc;
        }

        public String getZcd()
        {
            return zcd;
        }

        public void setZcd(String zcd)
        {
            this.zcd = zcd;
        }

        public String getXqj()
        {
            return xqj;
        }

        public void setXqj(String xqj)
        {
            this.xqj = xqj;
        }
    }

    /**
     * Created by 张孟尧 on 2016/5/19.
     */
    public static class ScheduleDetail {
        private TextView textView;
        private int id;
        private int color;
        private ScheduleItem scheduleItem;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ScheduleItem getScheduleItem() {
            return scheduleItem;
        }

        public void setScheduleItem(ScheduleItem scheduleItem) {
            this.scheduleItem = scheduleItem;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}
