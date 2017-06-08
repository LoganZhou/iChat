/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Utils.iChatUser;
import java.util.EventObject;

/**
 * 私聊窗口关闭事件
 * @author ZhouHeng
 */
public class ClosePrivateWindowEvent extends EventObject{
    private iChatUser targetUser;
    
    public ClosePrivateWindowEvent(Object source, iChatUser targetUser) {
        super(source);
        this.targetUser = targetUser;
    }

    public iChatUser getTargetUser() {
        return targetUser;
    }
    
}
