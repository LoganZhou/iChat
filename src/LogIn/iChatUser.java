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
 *
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
        Connection conn = getConn();
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

    private Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://119.29.8.35:3306/iChat?useSSL=false";
        String username = "iChatAdmin";
        String password = "hzauiChat8756";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public int signIn() {
        Connection conn = getConn();
        int i = 0;
        long userID;
        String sql = "insert into user (User_name, Password) value(?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,userName);
            pstmt.setString(2,password);
            i = pstmt.executeUpdate();  //更新条数，如果为0，则失败
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("注册失败：用户名重复！");
        }
        return i;
    }
    
    public long returnID() {
        Connection conn = getConn();
        long userID;
        String UID = null;
        String getID = "select User_ID from iChat.`user` where iChat.`user`.`User_Name` = ?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(getID);
            pstmt.setString(1,userName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                UID = rs.getString(1);
            }
            userID = Long.valueOf(UID);
            pstmt.close();
            this.userID = userID;
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

    public int logIn() {
        Connection conn = getConn();
        String checkUserName = "SELECT 1 FROM iChat.`user` WHERE iChat.`user`.`User_Name` = ? LIMIT 1";
        String checkPassword = "SELECT * FROM iChat.`user` WHERE iChat.`user`.`User_Name` = ? AND iChat.`user`.`Password` = ?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(checkUserName);
            pstmt.setString(1,userName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                /*如果非空，则存在该用户*/
                pstmt = (PreparedStatement) conn.prepareStatement(checkPassword);
                pstmt.setString(1,userName);
                pstmt.setString(2,password);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("登陆成功！");
                    return 1;
                }
                else {
                    System.out.println("密码错误！");
                    return -2;
                }
            }
            else {
                System.out.println("该用户不存在！");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(iChatUser.class.getName()).log(Level.SEVERE, null, ex);
            }
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
