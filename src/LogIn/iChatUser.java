/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;
import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * iChatUser类
 * 存储用户信息
 * @author a8756
 */
public class iChatUser implements Serializable{
    private String userName;
    private String password;
    private long userID;

    /**
     * 登陆用User构造函数
     * @param userID
     * @param password 
     */
    public iChatUser(long userID, String password) {
        this.userID = userID;
        this.password = password;
        this.userName = ""; //default
    }
    
    public iChatUser(String userName, long userID) {
        this.userName = userName;
        this.userID = userID;
        this.password = "";
    }
    
    /**
     * 注册用User构造函数
     * @param userName
     * @param password 
     */
    public iChatUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof iChatUser)) {
            return false;
        }
        iChatUser tempUser = (iChatUser) obj;
        return this.userID == tempUser.userID && this.userName.equals(tempUser.userName);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + userName.hashCode();
        result = 37 * result + (int)(userID^(userID >>> 32));
        return result;
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
