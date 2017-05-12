package cn.zm.blog.service;


import cn.zm.blog.entity.User;
import cn.zm.blog.exception.NameException;
import cn.zm.blog.exception.PasswordException;
import cn.zm.blog.exception.UserExistException;

/**
 * Created by zm on 2017/5/12.
 */
public interface UserService {

    User login(String name, String password) throws NameException, PasswordException;

    User regist(String name, String password, String confirm) throws UserExistException, NameException, PasswordException;
}
