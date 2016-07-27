package com.swuos.ALLFragment.swujw.grade.model;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
/*用于Gson解析json数据*/
public class GradesData
{
    private List<Items> items;

    public List<Items> getItems()
    {
        return items;
    }

    public void setItems(List<Items> items)
    {
        this.items = items;
    }

    public static class Items
    {
        private String cj;
        private String jd;
        private String kcmc;
        private String xf;
        private String jxb_id;

        public String getJxb_id() {
            return jxb_id;
        }

        public void setJxb_id(String jxb_id) {
            this.jxb_id = jxb_id;
        }

        public String getCj() {
            return cj;
        }

        public void setCj(String cj) {
            this.cj = cj;
        }

        public String getJd() {
            return jd;
        }

        public void setJd(String jd) {
            this.jd = jd;
        }

        public String getKcmc() {
            return kcmc;
        }

        public void setKcmc(String kcmc) {
            this.kcmc = kcmc;
        }

        public String getXf() {
            return xf;
        }

        public void setXf(String xf) {
            this.xf = xf;
        }
    }

}
