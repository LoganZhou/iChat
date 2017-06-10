/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileTransfer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 * 客户端文件接收器（多线程）
 * @author ZhouHeng
 */
public class FileReceiver implements Runnable{
    private Socket socket;
    private DataInputStream dis;
    private FileOutputStream fos;
    private FileTransferProgress progressDialog;
    private File file;
    static private Logger logger = Logger.getLogger(FileReceiver.class);
    /**
     * 初始化文件接收器
     * @param progressDialog 传输进度对话框
     * @param file 保存文件
     */
    public FileReceiver(FileTransferProgress progressDialog, File file) {
        try {
            this.socket = new Socket("119.29.8.35",14849);
        } catch (IOException ex) {
            logger.error("连接文件传输中转服务器失败！", ex);
        }
        this.progressDialog = progressDialog;
        this.file = file;
    }
    
    private void saveFile() {
        //等待连接
        try {
            dis = new DataInputStream(socket.getInputStream());          
       
            //文件名和长度
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            logger.info("准备开始接收文件...");
            File saveFile = null;
            if (file.isDirectory()){
                logger.error("非法操作，该文件为路径！");
            }
            else {
                saveFile = file;
                logger.info("文件保存路径为：" + saveFile.getAbsolutePath());
            }
            fos = new FileOutputStream(saveFile);
            //接收文件
            long progress = 0;
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = dis.read(bytes,0,bytes.length)) != -1) {
                fos.write(bytes,0,length);
                fos.flush();
                progress += length;
                progressDialog.getProgressBar().setValue((int) (100*progress/fileLength));
            }
            //完成传输对话框，点击确定关闭
            progressDialog.dispose();
            JOptionPane.showMessageDialog(null, "文件接收成功！");
            logger.info("文件接收成功！");
        } catch (IOException e) {
            logger.error("文件传输IO流出错！", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (dis != null) {
                    dis.close();
                }
                socket.close();
            } catch (IOException e) {
                logger.error("文件传输IO流关闭出错！", e);
            }
        }
    }

    @Override
    public void run() {
        saveFile();
    }
}
