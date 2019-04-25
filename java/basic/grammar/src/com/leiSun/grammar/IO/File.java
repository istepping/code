package com.leiSun.grammar.IO;

import java.io.*;

import static com.leiSun.grammar.common.Assist.print;

/**
 * @author ���� on 2018/7/23
 * @version 1.0
 * @apiNote �ļ�������
 */
public class File {
    /**
     * @author ���� on 2018/7/23
     * @apiNote �ֽ��� InputStream->FileInputStream.OutputStream->FileOutputStream
     */
    public void fileOutputStream() throws IOException {
        /**�����*/
        OutputStream f = new FileOutputStream("outputStreamTest.txt");
        byte[] bytes = {'A', 'B', 'C', 'D', 'E'};
        f.write(bytes);
        f.flush();
        /**��������,OutputStreamWriter*/
        OutputStreamWriter writer = new OutputStreamWriter(f, "UTF-8");
        writer.append("\n��������");
        /**ˢ�»�����*/
        writer.flush();
        writer.close();
        f.close();
    }

    /**
     * @return void
     * @author ���� on 2018/7/23
     * @apiNote FileOutputStream
     */
    public void fileInputStream() throws IOException {
        /**����������*/
        InputStream inputStream = new FileInputStream("outputStreamTest.txt");
        /**�ַ���ȡ*/
//        int length=inputStream.available();
//        for(int i=0;i<length;i++){
//            print((char)inputStream.read()+" ");
//        }
        /**�����ȡ.;*/
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
