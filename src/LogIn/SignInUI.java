/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author a8756
 */
public class SignInUI extends javax.swing.JFrame {

    /**
     * Creates new form iChatSignIn
     */
    public SignInUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        signInSucceedDialog = new javax.swing.JDialog();
        signInSuccessfulButton = new javax.swing.JButton();
        userIDTextField = new javax.swing.JTextField();
        signInSuccessfulInfo = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        signInFailedDialog = new javax.swing.JDialog();
        signInFailedInfo = new javax.swing.JTextField();
        signInFailedButton = new javax.swing.JButton();
        nameLabel = new javax.swing.JLabel();
        passwdLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        passwdTextField = new javax.swing.JTextField();
        passwdNote = new javax.swing.JLabel();
        nameNote = new javax.swing.JLabel();
        signInButton = new javax.swing.JButton();

        signInSucceedDialog.setSize(new java.awt.Dimension(300, 250));

        signInSuccessfulButton.setText("确定");
        signInSuccessfulButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signInSuccessfulButtonMouseClicked(evt);
            }
        });

        userIDTextField.setEditable(false);
        userIDTextField.setFont(new java.awt.Font("宋体", 1, 18)); // NOI18N
        userIDTextField.setForeground(new java.awt.Color(255, 0, 0));
        userIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userIDTextFieldActionPerformed(evt);
            }
        });

        signInSuccessfulInfo.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        signInSuccessfulInfo.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("恭喜您，注册成功！\n以下是您的用户ID，请牢记。\n登陆时请使用您的用户ID登陆。");
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        signInSuccessfulInfo.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel1.setText("用户ID");

        javax.swing.GroupLayout signInSucceedDialogLayout = new javax.swing.GroupLayout(signInSucceedDialog.getContentPane());
        signInSucceedDialog.getContentPane().setLayout(signInSucceedDialogLayout);
        signInSucceedDialogLayout.setHorizontalGroup(
            signInSucceedDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signInSucceedDialogLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(signInSucceedDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(signInSucceedDialogLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(userIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(signInSuccessfulInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(signInSucceedDialogLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(signInSuccessfulButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        signInSucceedDialogLayout.setVerticalGroup(
            signInSucceedDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signInSucceedDialogLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(signInSuccessfulInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(signInSucceedDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(signInSuccessfulButton)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        signInFailedDialog.setSize(new java.awt.Dimension(290, 180));

        signInFailedInfo.setEditable(false);
        signInFailedInfo.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        signInFailedInfo.setText("注册失败！用户名已存在");
        signInFailedInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInFailedInfoActionPerformed(evt);
            }
        });

        signInFailedButton.setText("确定");
        signInFailedButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signInFailedButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout signInFailedDialogLayout = new javax.swing.GroupLayout(signInFailedDialog.getContentPane());
        signInFailedDialog.getContentPane().setLayout(signInFailedDialogLayout);
        signInFailedDialogLayout.setHorizontalGroup(
            signInFailedDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signInFailedDialogLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(signInFailedInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addGroup(signInFailedDialogLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(signInFailedButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        signInFailedDialogLayout.setVerticalGroup(
            signInFailedDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signInFailedDialogLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(signInFailedInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(signInFailedButton)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(400, 230));
        setSize(new java.awt.Dimension(400, 230));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        nameLabel.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        nameLabel.setText("用户名");

        passwdLabel.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        passwdLabel.setText("密码");

        nameTextField.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });

        passwdTextField.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N

        passwdNote.setText("密码长度最大为20位");

        nameNote.setText("用户名长度最大为20位");

        signInButton.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        signInButton.setText("注册");
        signInButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signInButtonMouseClicked(evt);
            }
        });
        signInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(146, Short.MAX_VALUE)
                        .addComponent(signInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(passwdLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameTextField)
                            .addComponent(passwdTextField))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameNote)
                    .addComponent(passwdNote))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameNote))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwdLabel)
                    .addComponent(passwdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwdNote))
                .addGap(18, 18, 18)
                .addComponent(signInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void signInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signInButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_signInButtonActionPerformed

    private void userIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userIDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userIDTextFieldActionPerformed

    private void signInFailedInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signInFailedInfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_signInFailedInfoActionPerformed

    private void signInButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInButtonMouseClicked
        /**
         * 注册按钮点击事件
         * 创建User对象，并调用signIn()方法注册
         */
        String userName = nameTextField.getText();
        String passwd = passwdTextField.getText();
        iChatUser newUser = new iChatUser(userName, passwd);
        if (signIn(newUser)!=0) {
            //注册成功
            userIDTextField.setText(String.valueOf(returnID(newUser)));
            signInSucceedDialog.setVisible(true);
        } 
    }//GEN-LAST:event_signInButtonMouseClicked

    private void signInSuccessfulButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInSuccessfulButtonMouseClicked
        /**
         * 注册成功确定按钮：关闭当前对话框以及注册窗口
         */
        signInSucceedDialog.setVisible(false);
        this.setVisible(false);
        
    }//GEN-LAST:event_signInSuccessfulButtonMouseClicked

    private void signInFailedButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInFailedButtonMouseClicked
        /**
         * 注册失败对话框确定按钮，关闭对话框
         */
        signInFailedDialog.setVisible(false);
    }//GEN-LAST:event_signInFailedButtonMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed
    
    private int signIn(iChatUser user) {
        /**
         * 注册模块
         * 参数： iChatUser user
         * 返回值： 表修改成功条数
         */
        Connection conn = iChatConnection.getConn();
        int i = 0;
        long userID;
        String sql = "insert into user (User_name, Password) value(?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,user.getUserName());
            pstmt.setString(2,user.getPassword());
            i = pstmt.executeUpdate();  //更新条数，如果为0，则失败
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            //e.printStackTrace();
            //System.out.println("注册失败：用户名重复！");
            //注册失败
            signInFailedDialog.setVisible(true);
        }
        return i;
    }
    
    private long returnID(iChatUser user) {
        Connection conn = iChatConnection.getConn();
        long userID;
        String UID = null;
        String getID = "select User_ID from iChat.`user` where iChat.`user`.`User_Name` = ?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(getID);
            pstmt.setString(1,user.getUserName());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                UID = rs.getString(1);
            }
            userID = Long.valueOf(UID);
            pstmt.close();
            user.setUserID(userID);
            return userID;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("查询失败！");
            return -1;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(iChatUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignInUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignInUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignInUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignInUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignInUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameNote;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel passwdLabel;
    private javax.swing.JLabel passwdNote;
    private javax.swing.JTextField passwdTextField;
    private javax.swing.JButton signInButton;
    private javax.swing.JButton signInFailedButton;
    private javax.swing.JDialog signInFailedDialog;
    private javax.swing.JTextField signInFailedInfo;
    private javax.swing.JDialog signInSucceedDialog;
    private javax.swing.JButton signInSuccessfulButton;
    private javax.swing.JScrollPane signInSuccessfulInfo;
    private javax.swing.JTextField userIDTextField;
    // End of variables declaration//GEN-END:variables
}
