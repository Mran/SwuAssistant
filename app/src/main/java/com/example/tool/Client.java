package com.example.tool;

import android.util.Log;

import com.example.swuassistant.Constant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntityHC4;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
public class Client
{
    /*新建一个httpClient连接*/
    private static HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    private static CloseableHttpClient httpClient = httpClientBuilder.useSystemProperties().build();

    /*发送GET请求*/
    public  String doGet(String url)
    {
        /*response是用来接收获得网页内容*/
        String response = null;
        /*创建一个GET*/
        HttpGetHC4 httpGet = new HttpGetHC4(url);
        try
        {
            /*发送GET请求*/
            CloseableHttpResponse response1 = httpClient.execute(httpGet);
            /*判断返回码是否是200,如果是就对返回的内容进行读取*/
            if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity httpEntity = response1.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String readLine;
                while ((readLine = bufferedReader.readLine()) != null)
                {
                    //response = br.readLine();
                    response = response + readLine;
                }
                inputStream.close();
                bufferedReader.close();
                response1.close();
                Log.d("client", response);
                return response;
            } else
            {
                return "连接出错";
            }

        } catch (UnknownHostException e)
        {
            /*捕获没有网络的出错信息*/
            return Constant.NO_NET;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return response;
    }

    public  String doPost(String url, List<NameValuePair> nameValuePairs)
    {
        /*response是用来接收获得网页内容*/
        String response = null;
        /*新建一个post请求*/
        HttpPostHC4 httpPost = new HttpPostHC4(url);
        //拼接参数
        try
        {
            /*对post参数体进行设置*/
            httpPost.setEntity(new UrlEncodedFormEntityHC4(nameValuePairs));
            /*发送post请求*/
            CloseableHttpResponse response2 = httpClient.execute(httpPost);
            /*对回复内容进行读取*/
            if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity httpEntity = response2.getEntity();
            /*把内容转化成流的形式*/
                InputStream inputStream = httpEntity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String readLine;
                while ((readLine = bufferedReader.readLine()) != null)
                {
                    response = response + readLine;
                }
            /*关闭流*/
                inputStream.close();
                bufferedReader.close();
                response2.close();
            } else
            {
                return "连接出错";
            }
            Log.d("client", response);

        } catch (UnknownHostException e)
        {
            /*捕获没有网络的出错信息*/
            return Constant.NO_NET;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return response;
    }

}

