/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import java.io.Serializable;

/**
 * iChatUser类
 * 存储用户信息
 * @author ZhouHeng
 */
public class iChatUser implements Serializable{
    private String userName;
    private String password;
    private long userID;

    /**
     * 登陆用User构造函数
     * @param userID 用户iD
     * @param password 密码
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
     * @param userName 用户名
     * @param password 密码
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
