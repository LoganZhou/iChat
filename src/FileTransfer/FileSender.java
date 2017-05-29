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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author a8756
 */
public class FileSender {
    private Socket socket;
    private FileInputStream fis;
    private DataOutputStream dos;
    private FileTransferProgress progressDialog;
    
    public FileSender(String IPAddress, FileTransferProgress progressDialog) {
        try {
            this.socket = new Socket(IPAddress, 4848);
        } catch (IOException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.progressDialog = progressDialog;
    }
    public void sendFile(File sendFile) {
        try {
            if (sendFile.exists()) {
                //初始化
                fis = new FileInputStream(sendFile);
                dos = new DataOutputStream(socket.getOutputStream());

                //文件名和长度
                dos.writeUTF(sendFile.getName());
                dos.flush();
                dos.writeLong(sendFile.length());
                dos.flush();

                //开始传输文件
                //System.out.println("=========== 开始传输 ==========");
                byte[] bytes = new byte[1024];
                int length = 0;
                long progress = 0;

                while ((length = fis.read(bytes,0,bytes.length)) != -1) {
                    dos.write(bytes,0,length);
                    dos.flush();
                    progress += length;
                    progressDialog.getProgressBar().setValue((int) (100*progress/sendFile.length()));
                    System.out.println("send:"+(100*progress/sendFile.length()));
                    //System.out.print("|" + (100*progress/sendFile.length()) + "% |");
                }
                //关闭进度窗口
                progressDialog.dispose();
                JOptionPane.showMessageDialog(null, "文件发送成功！");
                
                //System.out.println();
                //System.out.println("=========== 传输成功 ==========");
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }
}
