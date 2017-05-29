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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 * 文件接收器（线程类）
 * @author a8756
 */
public class FileReceiver implements Runnable{
    private ServerSocket socket;
    private Socket sender;
    private DataInputStream dis;
    private FileOutputStream fos;
    private FileTransferProgress progressDialog;
    private File file;
    private boolean Ready = false;
    /**
     * 初始化文件接收器
     * @param socket
     * @param progressDialog 
     */
    public FileReceiver(ServerSocket socket, FileTransferProgress progressDialog, File file) {
        this.socket = socket;
        this.progressDialog = progressDialog;
        this.file = file;
        this.Ready = false;
    }
    
    private void saveFile() {
        //等待连接
        try {
            Ready = true;        //进入就绪态
            sender = socket.accept();
            dis = new DataInputStream(sender.getInputStream());          
       
            //文件名和长度
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            System.out.println("Receiver:" + file.getAbsoluteFile());
            File saveFile = null;
            if (file.isDirectory()){
                saveFile = new File(file.getAbsolutePath()+fileName);
                System.out.println("dir="+file.getCanonicalPath());
            }
            else {
                saveFile = file;
                System.out.println(file.getAbsoluteFile());
            }
            fos = new FileOutputStream(saveFile);
            
            //弹出进度条对话框
//            receiveProgressBar.setVisible(true);
//            if (receiveProgressBar == null) {
//                System.out.println("bar is null");
//            }
            //接收文件
            long progress = 0;
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = dis.read(bytes,0,bytes.length)) != -1) {
                fos.write(bytes,0,length);
                fos.flush();
                progress += length;
                progressDialog.getProgressBar().setValue((int) (100*progress/fileLength));
                System.out.println("save:"+(100*progress/fileLength));
            }
            //完成传输对话框，点击确定关闭
            progressDialog.dispose();
            JOptionPane.showMessageDialog(null, "文件接收成功！");
            
            //System.out.println("========== 文件接收成功 ===========");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (dis != null) {
                    dis.close();
                }
                sender.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean is_Ready() {
        return Ready;
    }

    @Override
    public void run() {
        saveFile();
    }
}
