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
 * @author ���� on 2018/7/24
 * @version 1.0
 * @apiNote ��������
 */
public class ServiceSocket extends Thread{
    /**�����socket����*/
    private ServerSocket serverSocket;
    public ServiceSocket(int port) throws IOException{
        serverSocket=new ServerSocket(port);
        /**�����������ʱ��*/
        serverSocket.setSoTimeout(10000);
    }
    public void run(){
        while (true){
            try {
                print("�ȴ��ͻ���������(�˿ں�:"+serverSocket.getLocalPort()+")......");
                Socket server=serverSocket.accept();
                print("�ͻ��˵�ַ:"+server.getRemoteSocketAddress());
                /**����������*/
                DataInputStream in=new DataInputStream(server.getInputStream());
                print("����UTF:"+in.readUTF());
                /**���������*/
                DataOutputStream out=new DataOutputStream(server.getOutputStream());
                out.writeUTF("лл������!");
                server.close();
            }catch (SocketTimeoutException s){
                print("���ӳ�ʱ!");
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
