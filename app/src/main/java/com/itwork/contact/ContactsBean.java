package com.itwork.contact;

/**
 * Created by yuan.zhen.xing on 2018-07-17.
 */

public class ContactsBean implements Comparable<ContactsBean>{

    public String name;
    public String pinyin;

    public ContactsBean(String name){
        this.name = name;
        this.pinyin = PinYinUtils.getPinYin(name);

    }

    @Override
    public int compareTo(ContactsBean another) {
        return this.pinyin.compareTo(another.pinyin);
    }
}

