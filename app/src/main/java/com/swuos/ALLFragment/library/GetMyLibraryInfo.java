package com.swuos.ALLFragment.library;

import com.swuos.swuassistant.Constant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by ASUS on 2016/3/11.
 */
public class GetMyLibraryInfo {
    private static HttpClient httpClient;

    public static void Init(){
        httpClient=new DefaultHttpClient();
    }

    //登录到图书馆主页
    public static String libraryLogin(List<NameValuePair> nameValuePairs) {
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
    }

    //跳转到我的图书馆页面主页
    public static String libraryBorrowInfo(){
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
        return "nothing";
    }

    //跳转到我的书架
    public static String ToMyBookShelf(){
        HttpGet httpGet = new HttpGet(Constant.libraryBorrowUri);
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
    }

    public static String getMyBorrowHistory(){
        HttpGet httpGet = new HttpGet(Constant.libraryBorrowHistoryUri);
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
    }
}
