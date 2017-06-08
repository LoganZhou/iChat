/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import java.util.EventObject;

/**
 * 登陆事件
 * 触发登陆方法
 * @author ZhouHeng
 */
public class LoginEvent extends EventObject {
    /**
     * 初始化登录事件
     * @param sourObject 事件来源
     */
    public LoginEvent(Object sourObject) {
        super(sourObject);
    }  
}
