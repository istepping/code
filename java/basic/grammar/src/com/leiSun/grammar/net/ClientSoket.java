package com.leiSun.grammar.net;

import java.io.*;
import java.net.Socket;

import static com.leiSun.grammar.common.Assist.print;

/**
 * @author 孙磊 on 2018/7/24
 * @version 1.0
 * @apiNote 客户端
 */
public class ClientSoket {
    public static void main(String[] args){
        String serverName=args[0];
        int port=Integer.parseInt(args[1]);
        try {
            print("连接到服务器:"+serverName+",端口号:"+port);
            Socket client=new Socket(serverName,port);
            print("服务器地址:"+client.getRemoteSocketAddress());
            DataOutputStream out=new DataOutputStream(client.getOutputStream());
            out.writeUTF("hello!");
            DataInputStream in=new DataInputStream(client.getInputStream());
            print("服务器回应:"+in.readUTF());
            client.close();
        }catch (IOException i){
            i.printStackTrace();
        }
    }
}
