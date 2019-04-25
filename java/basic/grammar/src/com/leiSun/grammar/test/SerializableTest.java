package com.leiSun.grammar.test;

import com.leiSun.grammar.IO.SerializableObject;

import java.io.*;

import static com.leiSun.grammar.common.Assist.print;

/**
 * @author ���� on 2018/7/24
 * @version 1.0
 * @apiNote ���л��Ĳ��Ժ���
 */
public class SerializableTest {
    public static void main(String[] args)throws IOException {
        SerializableObject so=new SerializableObject("����","������",12345);
        /**���л�*/
        /**�ļ����������*/
        OutputStream os=new FileOutputStream("serializableObject.ser");
        /**���л�����*/
        ObjectOutputStream oos=new ObjectOutputStream(os);
        oos.writeObject(so);
        os.flush();
        oos.close();
        os.close();
        /**�ļ�����������*/
        InputStream is=new FileInputStream("serializableObject.ser");
        /**�����л�����*/
        ObjectInputStream ois=new ObjectInputStream(is);
        try {
            print(ois.readObject().toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
