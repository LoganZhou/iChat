/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Utils.iChatUser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * 自定义单元渲染器
 * @author ZhouHeng
 */
public class iChatUserListRenderer extends JPanel implements ListCellRenderer {
    private JLabel userLabel;
    private int width;
    private int height;
    private Font font;
    public iChatUserListRenderer(int width, int height, Font font) {
        this.setPreferredSize(new Dimension(width,height));
        this.width = width;
        this.height = height;
        this.font = font;
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
        userLabel.setBounds(5, 5, this.width, this.height);
        userLabel.setFont(font);
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
