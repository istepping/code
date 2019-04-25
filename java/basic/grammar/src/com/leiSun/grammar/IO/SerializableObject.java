package com.leiSun.grammar.IO;

import java.io.Serializable;

/**
 * @author ���� on 2018/7/24
 * @version 1.0
 * @apiNote java���л�
 */
public class SerializableObject implements Serializable {
    /**ֻ�м̳���Seralizable�ӿڵ���ſ��Ա����л�(String,����,Enum,Serializable4�����Ա����л�)*/
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






