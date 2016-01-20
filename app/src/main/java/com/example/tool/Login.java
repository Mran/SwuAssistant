package com.example.tool;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
/*本类实现了三个功能
* 1.登陆教务管理系统
* 2.获取成绩
* 3.获取基本信息(学号、姓名)*/
public class Login
{
    //    校内门户地址
    private static final String urlUrp = "http://urp6.swu.edu.cn/login.portal";
    //    用户信息发送目标地址
    private static final String urlLogin = "http://urp6.swu.edu.cn/userPasswordValidate.portal";
    //    登陆后跳转网页
    private static final String urlPortal = "http://urp6.swu.edu.cn/index.portal";
    //    #教务系统网站 Ems意为swu Educational management system
    private static final String urlEms = "http://jw.swu.edu.cn/jwglxt/idstar/index.jsp";
    private static final String urlJW = "http://jw.swu.edu.cn/jwglxt/xtgl/index_initMenu.html";
    /*登陆校内门户是post的两个重要参数*/
    private static final String gotos = "http://urp6.swu.edu.cn/loginSuccess.portal";
    private static final String gotoOnFail = "http://urp6.swu.edu.cn/loginFailure.portal";
    /*新建一个连接*/
    private static Client client = new Client();
    /*TotalInfo类用来保存用户的基本信息*/
    private static TotalInfo totalInfo = new TotalInfo();
    /*用来获取数据信息*/
    private static AllData allData = new AllData();

    /*登录*/
    public String doLogin(String userName, String userPassword)
    {
        /*构建post的参数*/
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("goto", gotos));
        nameValuePairs.add(new BasicNameValuePair("gotoOnFail", gotoOnFail));
        nameValuePairs.add(new BasicNameValuePair("Login.Token1", userName));
        nameValuePairs.add(new BasicNameValuePair("Login.Token2", userPassword));
        return Client.doPost(urlLogin, nameValuePairs);
    }

    public static TotalInfo getTotalInfo()
    {
        /*进入教务系统*/
        Client.doGet(urlEms);
        /*获得基本信息名字和学号*/
        AllData.setBasicInfo(totalInfo, client);
        return totalInfo;
    }

    public List<Grades> getGradesList()
    {
        /*储存成绩的列表*/
        List<Grades> gradesList = new ArrayList<>();
        /*成绩总和*/
        double cjCount = 0.0;
        /*学分总和*/
        double xfCount = 0.0;
        /*绩点总和*/
        double jdCount = 0.0;
        /*获取成绩信息并保存到totalInfo*/
        AllData.setGrades(totalInfo, client);
        /*已获得的成绩信息进行整理*/
        GradesData gradesData = totalInfo.getGrades();
        /*设置列表的头部*/
        Grades gradesHeader = new Grades();
        gradesHeader.setKcmc("科目   ");
        gradesHeader.setCj("成绩");
        gradesHeader.setXf("学分");
        gradesHeader.setJd("绩点");
        gradesList.add(gradesHeader);
        GradesData.Items items;
        for (int i = 0; i < gradesData.getItems().size(); i++)
        {
            /*获得单个的科目成绩*/
            items = gradesData.getItems().get(i);
            String kcmc = items.getKcmc();
            String cj = items.getCj();
            String xf = items.getXf();
            String jd = items.getJd();
            /*用来处理成绩是按照ＡＢＣＤ评定的情况*/
            switch (cj)
            {
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
            xfCount += Double.valueOf(xf);
            jdCount += Double.valueOf(jd);
            Grades grades = new Grades();
            grades.setKcmc(kcmc);
            grades.setCj(cj);
            grades.setXf(xf);
            grades.setJd(jd);
            /*添加到列表中*/
            gradesList.add(grades);
        }
        /*设置列表的尾部，显示平均成绩和总成绩*/
        Grades gradesFooter1 = new Grades();
        gradesFooter1.setKcmc("平均成绩");
        gradesFooter1.setCj(String.valueOf(cjCount / gradesData.getItems().size()));
        gradesFooter1.setXf(String.valueOf(xfCount / gradesData.getItems().size()));
        gradesFooter1.setJd(String.valueOf(jdCount / gradesData.getItems().size()));
        gradesList.add(gradesFooter1);

        Grades gradesFooter2 = new Grades();
        gradesFooter2.setKcmc("总成绩");
        gradesFooter2.setCj(String.valueOf(cjCount));
        gradesFooter2.setXf(String.valueOf(xfCount));
        gradesFooter2.setJd(String.valueOf(jdCount));
        gradesList.add(gradesFooter2);
        return gradesList;

    }
}

