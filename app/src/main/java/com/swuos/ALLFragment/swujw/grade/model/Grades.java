package com.swuos.ALLFragment.swujw.grade.model;

import com.google.gson.Gson;
import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by 张孟尧 on 2016/1/23.
 */
public class Grades {

    private static OkhttpNet okhttpNet;

    public Grades(OkhttpNet okhttpNet) {
        this.okhttpNet = okhttpNet;
        /*进入教务系统*/
        okhttpNet.doGet(Constant.urlEms);
    }

    public static List<GradeItem> getGradesList(TotalInfos totalInfo) {

        /*构建gson数据来解析json数据*/
        Gson gson = new Gson();
        totalInfo.setGrades(gson.fromJson(totalInfo.getGradesDataJson(), GradesData.class));
        /*储存成绩的列表*/
        List<GradeItem> gradeItemList = new ArrayList<>();
        /*成绩总和*/
        double cjCount = 0.0;
        /*学分总和*/
        double xfCount = 0.0;
        /*绩点总和*/
        double jdCount = 0.0;
        /*已获得的成绩信息进行整理*/
        GradesData gradesData = totalInfo.getGrades();
        /*设置列表的头部*/

        GradesData.Items items;
        for (int i = 0; i < gradesData.getItems().size(); i++) {
            /*获得单个的科目成绩*/
            items = gradesData.getItems().get(i);
            String kcmc = items.getKcmc();
            String cj = items.getCj();
            String xf = items.getXf();
            String jd = items.getJd();
            String xh_id = items.getXh_id();
            String jxb_id = items.getJxb_id();
            String xnm = items.getXnm();
            String xqm = items.getXqm();
            /*用来处理成绩是按照ＡＢＣＤ评定的情况*/
            switch (cj) {
                case "A":
                    cjCount += 95;
                    break;
                case "B":
                    cjCount += 85;
                    break;
                case "C":
                    cjCount += 75;
                    break;
                case "D":
                    cjCount += 65;
                    break;
                case "E":
                    cjCount += 55;
                    break;
                default:
                    cjCount += Double.valueOf(cj);
                    break;
            }
            /*对成绩进行总和相加*/
            jdCount += Double.valueOf(jd);
            /*绩点不等于0时加学分*/
            if (!jd.contains("0")) {
                xfCount += Double.valueOf(xf);
            }
            GradeItem gradeItem = new GradeItem();
            gradeItem.setKcmc(kcmc);
            gradeItem.setCj(cj);
            gradeItem.setXf(xf);
            gradeItem.setJd(jd);
            gradeItem.setJxb_id(jxb_id);
            gradeItem.setXh_id(xh_id);
            gradeItem.setXnm(xnm);
            gradeItem.setXqm(xqm);
            /*添加到列表中*/
            gradeItemList.add(gradeItem);
        }
        /*设置列表的尾部，显示平均成绩和总成绩*/
        GradeItem gradeItemFooter1 = new GradeItem();
        gradeItemFooter1.setKcmc("平均");
        gradeItemFooter1.setCj(String.format("%.2f", cjCount / gradesData.getItems().size()));
        gradeItemFooter1.setXf(String.format("%.2f", xfCount / gradesData.getItems().size()));
        gradeItemFooter1.setJd(String.format("%.2f", jdCount / gradesData.getItems().size()));
        gradeItemList.add(gradeItemFooter1);

        GradeItem gradeItemFooter2 = new GradeItem();
        gradeItemFooter2.setKcmc("总和");
        gradeItemFooter2.setCj(String.format("%.2f", cjCount));
        gradeItemFooter2.setXf(String.format("%.2f", xfCount));
        gradeItemFooter2.setJd(String.format("%.2f", jdCount));
        gradeItemList.add(gradeItemFooter2);
        return gradeItemList;
    }

    public static GradeItem getGradeDetial(String respones, GradeItem gradeItem) {

        List<String[]> detial = new ArrayList<>();
        //
        //        /*构建一个post的参数*/
        //        RequestBody requestBody = new FormBody.Builder()
        //                .add("jxb_id", gradeItem.getJxb_id())
        //                .add("kcmc", gradeItem.getKcmc())
        //                .add("xh_id", gradeItem.getXh_id())
        //                .add("xnm", gradeItem.getXnm())
        //                .add("xqm", gradeItem.getXqm())
        //                .build();
        //        String url = "http://jw.swu.edu.cn/jwglxt/cjcx/cjcx_cxCjxq.html?" + "time=" + String.valueOf(System.currentTimeMillis()) + "&gnmkdmKey=N305005" + "&sessionUserKey=" + gradeItem.getXh_id();
        //        /*构建目标网址*/
        //        /*发送请求*/
        //        String respones = okhttpNet.doPost(url, requestBody);
        if (!respones.contains(Constant.NO_NET)) {
            Document body = Jsoup.parse(respones, "UTF-8");
            Element subtab = body.getElementById("subtab");
            Elements tbody = subtab.getElementsByTag("tbody");
            for (Element links : tbody) {
                Elements tr = links.getElementsByTag("tr");
                for (Element link1 : tr) {
                    Elements td = link1.getElementsByTag("td");
                    String[] tmp = {td.get(0).text(), td.get(1).text(), td.get(2).text(),};
                    detial.add(tmp);
                }
            }
        }
        gradeItem.setDetial(detial);
        return gradeItem;
    }

    public String setGrades(TotalInfos totalInfo, String xnm, String xqm) {
        /*构建一个post的参数*/
        RequestBody requestBody = new FormBody.Builder()
                .add("_search", "false")
                .add("nd", Long.toString(new Date().getTime()))
                .add("queryModel.currentPage", "1")
                .add("queryModel.showCount", "1000")
                .add("queryModel.sortName", "")
                .add("queryModel.sortOrder", "asc")
                .add("time", "0")
                .add("xnm", xnm)
                .add("xqm", xqm)
                .build();
        /*构建目标网址*/
        String url = "http://jw.swu.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?" + "doType=query&gnmkdmKey=N305005&sessionUserKey=" + totalInfo.getSwuID();
        /*发送请求*/
        String respones = okhttpNet.doPost(url, requestBody);
        if (!respones.contains(Constant.NO_NET)) {
            totalInfo.setGradesDataJson(respones);
        } else
            return respones;
        return Constant.CLIENT_OK;
    }
}
