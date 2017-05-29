/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GroupChat;

import LogIn.iChatUser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author zhouheng
 */
public class GroupChatListRenderer extends JPanel implements ListCellRenderer {
    private JLabel userLabel;
    
    public GroupChatListRenderer() {
        this.setPreferredSize(new Dimension(150,40));
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, 
                                                  Object value, 
                                                  int index, 
                                                  boolean isSelected, 
                                                  boolean cellHasFocus) {
        iChatUser user = (iChatUser)value;
        String userInfo = user.getUserName() + "(" + user.getUserID() + ")";
        userLabel = new JLabel(userInfo);
        userLabel.setBounds(5, 5, 250, 30);
        userLabel.setFont(new Font("微软雅黑",0,18));
        removeAll();
        add(userLabel);
        
        /*判断是否选中*/
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
        }
        return this;
    }
}
