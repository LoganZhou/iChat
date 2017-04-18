/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import LogIn.iChatUser;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.AbstractListModel;

/**
 * 自定义表格
 * @author a8756
 */
public class iChatFriendsListModel extends AbstractListModel {
    private ArrayList<iChatUser> onlineUsers;         //在线列表
    
    public iChatFriendsListModel(HashSet<iChatUser> onlineUsers) {
        this.onlineUsers = new ArrayList<iChatUser>(onlineUsers);
    }
    
    @Override
    public int getSize() {
        return onlineUsers.size();
    }

    @Override
    public Object getElementAt(int index) {
        return onlineUsers.get(index);
    }
    
}
