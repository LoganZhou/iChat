/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import java.util.EventListener;
import java.util.EventObject;

/**
 * 登陆事件
 * 触发登陆方法
 * @author a8756
 */
public class LoginEvent extends EventObject {
    /**
     *
     * @param sourObject
     * @param loginStatus
     */
    public LoginEvent(Object sourObject) {
        super(sourObject);
    }  
}
