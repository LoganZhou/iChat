/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import LogIn.iChatUser;
import PrivateChat.iChatMessage;
import PrivateChat.iChatPrivateChatUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 * 主程序窗口
 * @author a8756
 */
public class MainUI extends javax.swing.JFrame implements ClosePrivateWindowListener{
    private Socket socket;              //私聊服务器连接
    private iChatIOManager ioManager;        //主程序模块
    private iChatUser user;             //当前登陆用户
    private iChatFriendsListModel friendsListModel; 
    private JList friendsList;          //在线用户List
    private HashSet<iChatUser> onlineListSet;   //在线用户数据容器
    
    /**
     * Creates new form MainUI
     */
    public MainUI(Socket socket, iChatUser user, HashSet<iChatUser> onlineListSet) {
        this.socket = socket;
        this.user = user;
        this.ioManager = new iChatIOManager(socket, user, this);
        this.onlineListSet = onlineListSet;
        initComponents();
        String welcomeStr = user.getUserName() + welcomeLabel.getText();
        welcomeLabel.setText(welcomeStr);
        friendsListModel = new iChatFriendsListModel(onlineListSet);
        friendsList = new JList(friendsListModel);
        friendsList.setCellRenderer(new iChatUserListRenderer(250,40,new Font("微软雅黑",0,18)));
        friendsList.setSize(jListPanel.getWidth(), jListPanel.getHeight());
        //friendsList.setPreferredSize(new Dimension(245,450));
        //friendsList.setBounds(15, 50, 245, 450);

        /*添加滚动条*/
        JScrollPane jp = new JScrollPane(friendsList);
        jp.setSize(jListPanel.getWidth(), jListPanel.getHeight());
        //jp.setPreferredSize(new Dimension(270,535));
        //jp.setBounds(15, 50, 255, 450);
        
        //add(friendsList);
        friendsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {   //When double click JList  
                    jListDoubleClick(friendsList.getSelectedValue());           //Event  
                }
            }
        });
        jListPanel.setLayout(new BorderLayout());
        jListPanel.add(jp,BorderLayout.CENTER);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    public void updateOnlineList(HashSet<iChatUser> newList) {
        System.out.println("update");
        friendsListModel = new iChatFriendsListModel(newList);
        friendsList.setModel(friendsListModel);
        //更新群聊的List
        ioManager.updateGroupChatOnlineList(newList);
    }
    
    /**
     * JList鼠标双击事件
     * 打开一个窗口
     */
    private void jListDoubleClick(Object value) {
        System.out.println("double click");
        iChatUser targetUser = (iChatUser)value;
        //窗口关闭监听管理器
        ClosePrivateWindowManager manager = new ClosePrivateWindowManager();
        manager.addClosePrivateWindowListener(this);
        ioManager.putValue(targetUser, new iChatPrivateChatUI(ioManager,targetUser,manager));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupChatBt = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jListPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("iChat");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(315, 500));
        setPreferredSize(new java.awt.Dimension(315, 700));
        setSize(new java.awt.Dimension(315, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        groupChatBt.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        groupChatBt.setText("进入聊天室");
        groupChatBt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupChatBtMouseClicked(evt);
            }
        });
        groupChatBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupChatBtActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        jLabel1.setText("在线用户列表");
        jLabel1.setPreferredSize(new java.awt.Dimension(108, 25));

        jListPanel.setBackground(new java.awt.Color(153, 255, 153));
        jListPanel.setPreferredSize(new java.awt.Dimension(270, 480));

        javax.swing.GroupLayout jListPanelLayout = new javax.swing.GroupLayout(jListPanel);
        jListPanel.setLayout(jListPanelLayout);
        jListPanelLayout.setHorizontalGroup(
            jListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jListPanelLayout.setVerticalGroup(
            jListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        welcomeLabel.setFont(new java.awt.Font("微软雅黑", 1, 30)); // NOI18N
        welcomeLabel.setText("，欢迎您");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(groupChatBt)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(welcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(welcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addComponent(groupChatBt)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void groupChatBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupChatBtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_groupChatBtActionPerformed

    private void groupChatBtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupChatBtMouseClicked
        // 点击进入群聊
        ioManager.createGroupChatWindow(onlineListSet);
    }//GEN-LAST:event_groupChatBtMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // 关闭主程序窗口
        int choice = JOptionPane.showConfirmDialog(null, "您要退出程序吗？","是否继续？",JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            //确定关闭，发送关闭通知
            ioManager.sendMessage(new iChatMessage(iChatMessage.CLOSE_CONNECTION));
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainUI().setVisible(true);
//            }
//        });
//    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton groupChatBt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jListPanel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void closingPrivateWindow(ClosePrivateWindowEvent event) {
        /**
         * 实现关闭窗口方法
         */
        ioManager.removeValue(event.getTargetUser());//删除容器内该窗口   
    }

}
