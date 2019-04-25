package com.leiSun.grammar.net;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static com.leiSun.grammar.common.Assist.print;

/**
 * @author 孙磊 on 2018/7/24
 * @version 1.0
 * @apiNote 服务器端
 */
public class ServiceSocket extends Thread{
    /**服务端socket对象*/
    private ServerSocket serverSocket;
    public ServiceSocket(int port) throws IOException{
        serverSocket=new ServerSocket(port);
        /**设置最大连接时间*/
        serverSocket.setSoTimeout(10000);
    }
    public void run(){
        while (true){
            try {
                print("等待客户端连接我(端口号:"+serverSocket.getLocalPort()+")......");
                Socket server=serverSocket.accept();
                print("客户端地址:"+server.getRemoteSocketAddress());
                /**输入流对象*/
                DataInputStream in=new DataInputStream(server.getInputStream());
                print("输入UTF:"+in.readUTF());
                /**输出流对象*/
                DataOutputStream out=new DataOutputStream(server.getOutputStream());
                out.writeUTF("谢谢连接我!");
                server.close();
            }catch (SocketTimeoutException s){
                print("连接超时!");
                break;
            }catch (IOException i){
                i.printStackTrace();
                break;
            }
        }
    }
    public static void main(String[] args){
        int port=Integer.parseInt(args[0]);
        try {
            Thread t=new ServiceSocket(port);
            t.run();
        }catch (IOException i){
            i.printStackTrace();
        }
    }
}
