package com.swuos.ALLFragment.library;

import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import okhttp3.RequestBody;

/**
 * Created by ASUS on 2016/3/11.
 */
public class GetMyLibraryInfo {
    //    private static HttpClient httpClient;
    private static OkhttpNet okhttpNet;
    public static void Init(){
        //        httpClient=new DefaultHttpClient();
        okhttpNet = new OkhttpNet();
    }

    //登录到图书馆主页
    public static String libraryLogin(RequestBody requestBody) {
/*
        HttpPost httpPost = new HttpPost(Constant.loginLibrary);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String string = EntityUtils.toString(entity);
                return string;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothing";
*/
        return okhttpNet.doPost(Constant.loginLibrary, requestBody);

    }

    //跳转到我的图书馆页面主页
    public static String libraryBorrowInfo(){
        /*
        HttpGet httpGet = new HttpGet(Constant.libraryBorrowInfo);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GB2312"));  //转换为中文编码
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothing";*/
        return okhttpNet.doGet(Constant.libraryBorrowInfo);
    }

    //跳转到我的书架
    public static String ToMyBookShelf(){
        /*HttpGet httpGet = new HttpGet(Constant.libraryBorrowUri);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GB2312"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothing";
        */
        return okhttpNet.doGet(Constant.libraryBorrowUri);
    }

    public static String getMyBorrowHistory(){
/*        HttpGet httpGet = new HttpGet(Constant.libraryBorrowHistoryUri);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GB2312"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothing";*/
        return okhttpNet.doGet(Constant.libraryBorrowHistoryUri);
    }
}
