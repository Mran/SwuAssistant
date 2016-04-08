package com.swuos.net;

import android.util.Log;

import com.swuos.swuassistant.Constant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntityHC4;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtilsHC4;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/1/6.
 */
public class Client
{
    /*新建一个httpClient连接*/

    /*设置请求配置,设置了连接超时和读取超时*/
    private RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(Constant.TIMEOUT)
            .setSocketTimeout(Constant.TIMEOUT)
            .build();
    private HttpClientBuilder httpClientBuilder;
    private CloseableHttpClient httpClient;

    public Client()
    {
        /*初始化连接*/
        httpClientBuilder = HttpClientBuilder.create();
        httpClient = httpClientBuilder.useSystemProperties().build();
    }

    /*发送GET请求*/
    public String doGet(String url)
    {
        /*response是用来接收获得网页内容*/
        String response = null;

        /*创建一个GET*/
        HttpGetHC4 httpGet = new HttpGetHC4(url);
        /*设置超时*/
        httpGet.setConfig(requestConfig);
        try
        {
            /*发送GET请求*/
            CloseableHttpResponse response1 = httpClient.execute(httpGet);
            /*判断返回码是否是200,如果是就对返回的内容进行读取*/
            if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                /*获得响应体*/
                HttpEntity httpEntity = response1.getEntity();
                /*获取响应体的正文*/
                response = EntityUtilsHC4.toString(httpEntity);
                /*消耗掉响应体*/
                EntityUtilsHC4.consume(httpEntity);
                /*释放资源*/
                response1.close();
                Log.d("client_doGet()", response);
                return response;
            } else
            {
                return Constant.CLIENT_ERROR;
            }

        } catch (SocketTimeoutException e)
        {
            /*连接超时*/
            return Constant.CLIENT_TIMEOUT;
        } catch (SocketException e)
        {
            /*连接超时*/
            return Constant.CLIENT_TIMEOUT;
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

    public String doPost(String url, List<NameValuePair> nameValuePairs)
    {
        /*response是用来接收获得网页内容*/
        String response = null;
        /*新建一个post请求*/
        HttpPostHC4 httpPost = new HttpPostHC4(url);
        /*设置超时*/
        httpPost.setConfig(requestConfig);
        try
        {
            /*对post参数体进行设置*/
            httpPost.setEntity(new UrlEncodedFormEntityHC4(nameValuePairs));
            /*发送post请求*/
            CloseableHttpResponse response2 = httpClient.execute(httpPost);
            /*对回复内容进行读取*/
            if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {

                /*获得响应体*/
                HttpEntity httpEntity = response2.getEntity();
                /*获取响应体的正文*/
                response = EntityUtilsHC4.toString(httpEntity);

                /*消耗掉响应体*/
                EntityUtilsHC4.consume(httpEntity);
                /*释放资源*/
                response2.close();
                Log.d("client_doPost()", response);
            } else
            {
                return Constant.CLIENT_ERROR;
            }
        } catch (SocketTimeoutException e)
        {
            /*连接超时*/
            return Constant.CLIENT_TIMEOUT;
        } catch (SocketException e)
        {
            /*连接超时*/
            System.out.print("timeout");
            return Constant.CLIENT_TIMEOUT;

        } catch (UnknownHostException e)
        {
            /*捕获网络问题的出错信息*/
            return Constant.NO_NET;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return response;
    }

}

