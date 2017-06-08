/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;


/**
 * 服务端文件中转器监听线程
 * 随服务端主线程启动而启动，监听文件传输请求，同一时刻只允许服务一对客户端，其余客户端需要等待
 * 收到文件传输请求后，将状态置为忙
 * 文件发送者连接端口：14848
 * 文件接收者连接端口：14849
 * @author ZhouHeng
 */
public class ServerFileTransfer implements Runnable{
    private ServerSocket  serverSocket_A;
    private ServerSocket  serverSocket_B;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket A;
    private Socket B;
    private FileTransferResource transferResource;//文件中转资源
    static private Logger transferLogger = Logger.getLogger(ServerFileTransfer.class);
    
    public ServerFileTransfer(FileTransferResource transferResource) {
        //初始化文件中转ServerSocket
        try {
            serverSocket_A = new ServerSocket(14848);
            serverSocket_B = new ServerSocket(14849);
        } catch (IOException ex) {
            transferLogger.error("socket初始化失败！", ex);
        }
        A = null;
        B = null;
        dis = null;
        dos = null;
        this.transferResource = transferResource;
        transferLogger.info("文件中转器初始化成功！");
    }
    
     private void startToTransferFile() {
        transferResource.handleRequest();
        try {
            B = serverSocket_B.accept();
            A = serverSocket_A.accept();
        } catch (IOException ex) {
            transferLogger.error("获取客户端socket失败！", ex);
        }
        
        //初始化IO连接
        try {
            dis = new DataInputStream(A.getInputStream());
            dos = new DataOutputStream(B.getOutputStream());
        } catch (IOException ex) {
            transferLogger.error("初始化IO失败！", ex);
        }
        
        //开始中转数据
        byte[] bytes = new byte[1024];
        int length = 0;
        try {
            //读取
            while ((length = dis.read(bytes,0,bytes.length)) != -1) {
                //发送
                dos.write(bytes,0,length);
                dos.flush();
            }
        } catch (IOException ex) {
            transferLogger.error("中转数据出错！", ex);
        }
        
        //传输完毕，释放socket资源,IO资源
        try {
            if (dis != null){
                dis.close();
            }
            if (dos != null) {
                dos.close();
            }
            A.close();
            B.close();
            transferLogger.info("文件中转成功！");
        } catch (IOException ex) {
            transferLogger.error("释放资源出错！", ex);
        }
    }

    @Override
    public void run() {
        while(true) {
            startToTransferFile();
        }
    }
}
