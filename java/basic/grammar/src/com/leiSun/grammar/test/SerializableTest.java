package com.leiSun.grammar.test;

import com.leiSun.grammar.IO.SerializableObject;

import java.io.*;

import static com.leiSun.grammar.common.Assist.print;

/**
 * @author 孙磊 on 2018/7/24
 * @version 1.0
 * @apiNote 序列化的测试函数
 */
public class SerializableTest {
    public static void main(String[] args)throws IOException {
        SerializableObject so=new SerializableObject("向亮","大连市",12345);
        /**序列化*/
        /**文件输出流对象*/
        OutputStream os=new FileOutputStream("serializableObject.ser");
        /**序列化工具*/
        ObjectOutputStream oos=new ObjectOutputStream(os);
        oos.writeObject(so);
        os.flush();
        oos.close();
        os.close();
        /**文件输入流对象*/
        InputStream is=new FileInputStream("serializableObject.ser");
        /**反序列化工具*/
        ObjectInputStream ois=new ObjectInputStream(is);
        try {
            print(ois.readObject().toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
