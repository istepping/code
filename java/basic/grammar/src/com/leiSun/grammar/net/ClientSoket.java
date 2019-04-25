package com.leiSun.grammar.net;

import java.io.*;
import java.net.Socket;

import static com.leiSun.grammar.common.Assist.print;

/**
 * @author ���� on 2018/7/24
 * @version 1.0
 * @apiNote �ͻ���
 */
public class ClientSoket {
    public static void main(String[] args){
        String serverName=args[0];
        int port=Integer.parseInt(args[1]);
        try {
            print("���ӵ�������:"+serverName+",�˿ں�:"+port);
            Socket client=new Socket(serverName,port);
            print("��������ַ:"+client.getRemoteSocketAddress());
            DataOutputStream out=new DataOutputStream(client.getOutputStream());
            out.writeUTF("hello!");
            DataInputStream in=new DataInputStream(client.getInputStream());
            print("��������Ӧ:"+in.readUTF());
            client.close();
        }catch (IOException i){
            i.printStackTrace();
        }
    }
}
