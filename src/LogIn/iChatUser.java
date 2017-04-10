/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * iChatUser类
 * 存储用户信息
 * @author a8756
 */
public class iChatUser {
    private String userName;
    private String password;
    private long userID;

    iChatUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.userID = 0; //default
    }

    private void selectAll() {
        Connection conn = iChatConnection.getConn();
        String sql = "select * from user";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("+--------------------------+");
            while (rs.next()) {
                for (int i=1;i<=col;i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println("\n+--------------------------+");
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public long getUserID() {
        return userID;
    }

    public String getPassword() {

        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
