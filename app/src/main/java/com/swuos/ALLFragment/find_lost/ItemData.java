package com.swuos.ALLFragment.find_lost;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/3/13.
 */
public class ItemData
{
    private List<items> items;

    public class items
    {
        /*信息id*/
        private String id;
        /*标题*/
        private String title;
        /*细节描述*/
        private String details;
        /*丢失时间*/
        private String time;
        /*丢失地点*/
        private String place;
        /*联系电话*/
        private String telNumber;
        /*发布信息类型*/
        private String type;

        public String getDetails()
        {
            return details;
        }

        public String getId()
        {
            return id;
        }

        public String getPlace()
        {
            return place;
        }

        public String getTelNumber()
        {
            return telNumber;
        }

        public String getTime()
        {
            return time;
        }

        public String getTitle()
        {
            return title;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public void setDetails(String details)
        {
            this.details = details;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public void setPlace(String place)
        {
            this.place = place;
        }

        public void setTelNumber(String telNumber)
        {
            this.telNumber = telNumber;
        }

        public void setTime(String time)
        {
            this.time = time;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }
    }

    public List<items> getItems()
    {
        return items;
    }

    public void setItems(List<items> items)
    {
        this.items = items;
    }
}