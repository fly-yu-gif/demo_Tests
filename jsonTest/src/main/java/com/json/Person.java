package com.json;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author jz
 * @create 2022-08-27 16:58
 */
public class Person {

    @JSONField(name = "AGE")
    private int age;

    @JSONField(name = "FULL NAME")
    private String fullName;

    @JSONField(name="DATE OF BIRTH", format="dd/MM/yyyy", ordinal = 3)
    //不加注解则使用变量名作为key
    private Date dateOfBirth;

    public Person(int age, String fullName, Date dateOfBirth) {
        super();
        this.age = age;
        this.fullName= fullName;
        this.dateOfBirth = dateOfBirth;
    }

    // 标准 getters & setters

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "[age:"+age+" name:"+fullName+" birth: "+dateOfBirth+']';
    }
}