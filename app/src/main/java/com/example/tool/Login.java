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
    private static String urlUrp = "http://urp6.swu.edu.cn/login.portal";
    //    用户信息发送目标地址
    private static String urlLogin = "http://urp6.swu.edu.cn/userPasswordValidate.portal";
    //    登陆后跳转网页
    private static String urlPortal = "http://urp6.swu.edu.cn/index.portal";
    //    #教务系统网站 Ems意为swu Educational management system
    private static String urlEms = "http://jw.swu.edu.cn/jwglxt/idstar/index.jsp";
    private static String urlJW = "http://jw.swu.edu.cn/jwglxt/xtgl/index_initMenu.html";
    /*登陆校内门户是post的两个重要参数*/
    private static String gotos = "http://urp6.swu.edu.cn/loginSuccess.portal";
    private static String gotoOnFail = "http://urp6.swu.edu.cn/loginFailure.portal";
    private static Client client = new Client();
    private static TotalInfo totalInfo = new TotalInfo();
    private static AllData allData = new AllData();
//    private static String userName = "xlh442";
//    private static String userPassword = "2yexlh521";

//    private static String userName = "mran";
//    private static String userPassword = "19951106z.";

    public String doLogin(String userName, String userPassword)
    {


//        client.doGet(urlUrp);

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("goto", gotos));
        nameValuePairs.add(new BasicNameValuePair("gotoOnFail", gotoOnFail));
        nameValuePairs.add(new BasicNameValuePair("Login.Token1", userName));
        nameValuePairs.add(new BasicNameValuePair("Login.Token2", userPassword));
        return client.doPost(urlLogin, nameValuePairs);


    }

    public static TotalInfo getTotalInfo()
    {
        client.doGet(urlEms);

        allData.setBasicInfo(totalInfo, client);
        return totalInfo;
    }

    public List<Grades> getGradesList()
    {

        List<Grades> gradesList = new ArrayList<Grades>();
        double cjCount = 0.0;
        double xfCount=0.0;
        double jdCount=0.0;
        allData.setGrades(totalInfo, client);

        GradesData gradesData = totalInfo.getGrades();
        Grades gradesHeader = new Grades();
        gradesHeader.setKcmc("科目   ");
        gradesHeader.setCj("成绩");
        gradesHeader.setXf("学分");
        gradesHeader.setJd("绩点");
        gradesList.add(gradesHeader);
        GradesData.Items items;
        for (int i = 0; i < gradesData.getItems().size(); i++)
        {
            items = gradesData.getItems().get(i);
            String kcmc = items.getKcmc();
            String cj = items.getCj();
            String xf = items.getXf();
            String jd = items.getJd();
            switch (cj)
            {
                case "A":
                    cjCount+=95;
                    break;
                case "B":
                    cjCount+=85;
                    break;
                case "C":
                    cjCount+=75;
                    break;
                case "D":
                    cjCount+=65;
                    break;
                case "E":
                    cjCount+=55;
                    break;
                default:
                    cjCount+=Double.valueOf(cj);
                    break;
            }

            xfCount+=Double.valueOf(xf);
            jdCount+=Double.valueOf(jd);
            Grades grades = new Grades();
            grades.setKcmc(kcmc);
            grades.setCj(cj);
            grades.setXf(xf);
            grades.setJd(jd);
            gradesList.add(grades);
        }
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

