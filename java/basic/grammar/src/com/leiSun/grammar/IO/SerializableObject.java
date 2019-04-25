package com.leiSun.grammar.IO;

import java.io.Serializable;

/**
 * @author 孙磊 on 2018/7/24
 * @version 1.0
 * @apiNote java序列化
 */
public class SerializableObject implements Serializable {
    /**只有继承了Seralizable接口的类才可以被序列化(String,数组,Enum,Serializable4个可以被序列化)*/
    private static final long serialVersionUID = 2912164127598660137L;
    private String name;
    private String address;
    private transient int SSN;
    public SerializableObject(){}
    public SerializableObject(String name, String address,int SSN){
        this.name=name;
        this.address=address;
        this.SSN=SSN;
    }
    @Override
    public String toString() {
        return this.getClass()+"\tname="+name+
                               "\taddress="+address+
                               "\tSSN="+SSN;
    }
}






