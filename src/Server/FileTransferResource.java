/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import org.apache.log4j.Logger;


/**
 * 当文件接收方同意请求后，服务器将产生一个处理信号
 * 采用生产者消费者模式，生产者为iChatServer，消费者为ServerFileTransfer
 * @author zhouheng
 */
public class FileTransferResource {
    //资源标记
    private boolean flag = false;
    static private Logger logger = Logger.getLogger(FileTransferResource.class);
    
    public synchronized void createRequest() {
        while (flag) {//判断当前是否已经有请求
            try {
                logger.info("当前有正在中转的任务");
                wait();
            } catch (InterruptedException ex) {
                logger.error("线程等待出错！", ex);
            }
        }
        flag = true;
        notifyAll();
        logger.info("准备处理文件中转...");
    }
    
    public synchronized void handleRequest() {
        while (!flag) {//当前如果没有文件中转请求
            try {
                logger.info("当前没有中转请求，进入等待状态。");
                wait();
            } catch (InterruptedException ex) {
                logger.error("线程等待出错！", ex);
            }
        }
        flag = false;
        notifyAll();
        logger.info("正在处理文件中转");
    }
}
