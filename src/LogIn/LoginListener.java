/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import java.util.EventListener;

/**
 * 登陆事件监听接口，负责监听LoginEvent事件
 * @author ZhouHeng
 */
public interface LoginListener extends EventListener {
    /**
     * 响应登陆事件
     * @param event 登陆事件
     */
    public void loginEvent(LoginEvent event);
}
