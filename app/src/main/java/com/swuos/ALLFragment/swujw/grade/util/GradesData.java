package com.swuos.ALLFragment.swujw.grade.util;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
/*用于Gson解析json数据*/
public class GradesData
{
    private List<Items> items;

    public static class Items
    {
        private String cj;
        private String jd;
        private String kcmc;
        private String xf;

        public void setCj(String cj)
        {
            this.cj = cj;
        }

        public String getCj()
        {
            return cj;
        }

        public void setJd(String jd)
        {
            this.jd = jd;
        }

        public String getJd()
        {
            return jd;
        }

        public void setKcmc(String kcmc)
        {
            this.kcmc = kcmc;
        }

        public String getKcmc()
        {
            return kcmc;
        }

        public void setXf(String xf)
        {
            this.xf = xf;
        }

        public String getXf()
        {
            return xf;
        }
    }

    public void setItems(List<Items> items)
    {
        this.items = items;
    }

    public List<Items> getItems()
    {
        return items;
    }

}
