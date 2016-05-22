package com.swuos.ALLFragment.card;

import com.swuos.net.OkhttpNet;
import com.swuos.util.SALog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codekk on 2016/5/3.
 * Email:  645326280@qq.com
 */
public class EcardTools implements Serializable {
    private OkhttpNet okhttpNet;
    private String lastIndex = "0";

    public EcardTools() {
        okhttpNet = new OkhttpNet();
        SALog.d("kklog", "!!!!!Inits()!!!!!!");
    }

    public List<EcardInfo> GetEcardInfos(String id, String pd) {
        List<EcardInfo> ecardInfos;
        InputStream in = okhttpNet.doGetInputStream("http://ecard.swu.edu.cn/search/oracle/queryresult.asp?cardno=" + id + "&password=" + pd);
        String s = "none";
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = null;
            if (in == null) {
                return null;
            } else {
                reader = new BufferedReader(new InputStreamReader(in, "gb2312"));
            }
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }
            s = builder.toString();
            SALog.d("kklog", "s===>" + s);
            if (s.contains("卡号密码不对")) {
                List<EcardInfo> temp1 = new ArrayList<>();
                return temp1;
            }
        } catch (UnsupportedEncodingException e) {
            SALog.d("kklog", "UnsupportedEncodingException");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ecardInfos = ParserTools.parserHtmlToEcardInfos(s, "td");
        return ecardInfos;
    }

    public List<ConsumeInfo> GetConsumeInfos(String index) {
        SALog.d("kklog", "EcardTools lastIndex===>" + lastIndex);
        List<ConsumeInfo> consumeInfos;
        InputStream in = okhttpNet.doGetInputStream("http://ecard.swu.edu.cn/search/oracle/finance.asp?offset=" + index);
        String s = "none";
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = null;
            if (in == null) {
                return null;
            } else {
                reader = new BufferedReader(new InputStreamReader(in, "gb2312"));
            }
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }
            s = builder.toString();
        } catch (UnsupportedEncodingException e) {
            SALog.d("kklog", "UnsupportedEncodingException");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lastIndex.equals(index)) {
            consumeInfos = ParserTools.parserHtmlToConsumeInfos(s, "TD");
        } else {
            consumeInfos = ParserTools.parserHtmlToConsumeInfos2(s, "TD");
        }
        return consumeInfos;
    }

    public String GetLastIndex() throws StringIndexOutOfBoundsException {
        InputStream in = okhttpNet.doGetInputStream("http://ecard.swu.edu.cn/search/oracle/finance.asp");
        String s = "none";
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = null;
            if (in == null) {
                return null;
            } else {
                reader = new BufferedReader(new InputStreamReader(in, "gb2312"));
            }
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }
            s = builder.toString();
        } catch (UnsupportedEncodingException e) {
            SALog.d("kklog", "UnsupportedEncodingException");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int start = s.indexOf("共有<font color=red>");
        int end = s.indexOf("</font>页&");
        String ss = s.substring(start + 18, end);
        lastIndex = ss;
        return ss;
    }
}
