package com.leiSun.grammar.IO;

import java.io.*;

import static com.leiSun.grammar.common.Assist.print;

/**
 * @author 孙磊 on 2018/7/23
 * @version 1.0
 * @apiNote 文件操作类
 */
public class File {
    /**
     * @author 孙磊 on 2018/7/23
     * @apiNote 字节流 InputStream->FileInputStream.OutputStream->FileOutputStream
     */
    public void fileOutputStream() throws IOException {
        /**输出流*/
        OutputStream f = new FileOutputStream("outputStreamTest.txt");
        byte[] bytes = {'A', 'B', 'C', 'D', 'E'};
        f.write(bytes);
        f.flush();
        /**中文输入,OutputStreamWriter*/
        OutputStreamWriter writer = new OutputStreamWriter(f, "UTF-8");
        writer.append("\n中文输入");
        /**刷新缓冲区*/
        writer.flush();
        writer.close();
        f.close();
    }

    /**
     * @return void
     * @author 孙磊 on 2018/7/23
     * @apiNote FileOutputStream
     */
    public void fileInputStream() throws IOException {
        /**输入流对象*/
        InputStream inputStream = new FileInputStream("outputStreamTest.txt");
        /**字符读取*/
//        int length=inputStream.available();
//        for(int i=0;i<length;i++){
//            print((char)inputStream.read()+" ");
//        }
        /**编码读取.;*/
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append((char) reader.read());
        }
        print(sb);
        reader.close();
        inputStream.close();
    }
}
