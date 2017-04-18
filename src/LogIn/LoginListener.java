/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import java.util.EventListener;

/**
 * 登陆监听接口，负责监听LoginEvent事件
 * @author a8756
 */
public interface LoginListener extends EventListener {
    public void loginEvent(LoginEvent event);
}
