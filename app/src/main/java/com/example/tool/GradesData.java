package com.example.tool;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
/*用于Gson来解析json数据*/
public class GradesData
{
//    private String currentPage;
//    private String currentResult;
//    private String entityOrField;
//    private String showCount;
//    private String sortName;
//    private String sortOrder;
//    private String totalPage;
//    private String totalResult;
    private List<Items> items;

    public static class Items
    {
//        private String bh_id;
//        private String bj;
        private String cj;
        private String jd;
//        private String jgmc;
//        private String jgpxzd;
//        private String jxb_id;
//        private String kch;
        private String kcmc;
//        private String listnav;
//        private String njdm_id;
//        private String njmc;
//        private String pageable;
//        private String totalResult;
//        private String xb;
        private String xf;
//        private String xh;
//        private String xm;
//        private String xnmmc;
//        private String xqmmc;
//        private String zyh_id;
//        private String zymc;
//        private List<QueryModel> queryModel;

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
    //    private static class QueryModel
//    {
//        private String currentPage;
//        private String currentResult;
//        private String entityOrField;
//        private String showCount;
//        private String totalPage;
//        private String totalResult;
//    }
//    public void setCurrentPage(String currentPage)
//    {
//        this.currentPage=currentPage;
//    }
//
//    public String getCurrentPage()
//    {
//        return currentPage;
//    }
//
//    public void setCurrentResult(String currentResult)
//    {
//        this.currentResult = currentResult;
//    }
//
//    public void setEntityOrField(String entityOrField)
//    {
//        this.entityOrField = entityOrField;
//    }
//    public void setItems(List<Items> items)
//    {
//        this.items=items;
//    }
//
//    public List<Items> getItems()
//    {
//        return items;
//    }
}
