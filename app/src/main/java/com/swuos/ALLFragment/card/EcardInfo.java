package com.swuos.ALLFragment.card;

import java.io.Serializable;

/**
 * Created by codekk on 2016/5/3.
 * Email:  645326280@qq.com
 */
public class EcardInfo implements Serializable{
    private String subjectName;
    private String subjectContent;

    public EcardInfo(String name,String content){
        subjectName=name;
        subjectContent=content;
    }

    public EcardInfo(){}

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectContent() {
        return subjectContent;
    }

    public void setSubjectContent(String subjectContent) {
        this.subjectContent = subjectContent;
    }
}
