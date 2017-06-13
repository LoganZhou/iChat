/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileTransfer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 * 客户端文件发送器（多线程）
 * 通信服务器：119.29.8.35：14848
 * @author ZhouHeng
 */
public class FileSender implements Runnable{
    private Socket socket;
    private FileInputStream fis;
    private DataOutputStream dos;
    private FileTransferProgress progressDialog;
    private File sendFile;
    static private Logger logger = Logger.getLogger(FileSender.class);
    
    public FileSender(File sendFile, FileTransferProgress progressDialog) {
        try {
            this.socket = new Socket("119.29.8.35", 14848);
        } catch (IOException ex) {
            logger.error("文件发送Socket初始化失败！", ex);
        }
        this.progressDialog = progressDialog;
        this.sendFile = sendFile;
    }
    
    public void sendFile() {
        try {
            if (sendFile.exists()) {
                //初始化
                logger.info("开始发送文件：" + sendFile.getPath());
                logger.debug("文件长度为：" + sendFile.length());
                
                fis = new FileInputStream(sendFile);
                dos = new DataOutputStream(socket.getOutputStream());

                //文件名和长度
                dos.writeUTF(sendFile.getName());
                dos.flush();
                dos.writeLong(sendFile.length());
                dos.flush();

                //开始传输文件
                byte[] bytes = new byte[1024];
                int length = 0;
                long progress = 0;

                while ((length = fis.read(bytes,0,bytes.length)) != -1) {
                    dos.write(bytes,0,length);
                    dos.flush();
                    progress += length;
                    progressDialog.getProgressBar().setValue((int) (100*progress/sendFile.length()));
                }
                //关闭进度窗口
                progressDialog.dispose();
                JOptionPane.showMessageDialog(null, "文件发送成功！");
                logger.info("文件发送成功！");
            }
        }catch (FileNotFoundException e) {
            logger.error("无法找到该文件！",e);
        } catch (IOException e) {
            logger.error("文件传输IO流出错！",e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (dos != null) {
                    dos.close();
                }
                socket.close();
            } catch (IOException e) {
                logger.error("文件传输IO流关闭出错！",e);
            }
        }
    }

    @Override
    public void run() {
        sendFile();
    }
}
