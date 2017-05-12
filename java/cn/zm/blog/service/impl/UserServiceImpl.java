package cn.zm.blog.service.impl;

import cn.zm.blog.dao.UserDao;
import cn.zm.blog.entity.User;
import cn.zm.blog.exception.NameException;
import cn.zm.blog.exception.PasswordException;
import cn.zm.blog.exception.UserExistException;
import cn.zm.blog.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by zm on 2017/5/12.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User login(String name, String password) throws NameException, PasswordException {
        //为了测试aop执行顺序
        System.out.println("login");
        // 根据用户输入的用户名和密码检查是否登录
        if (name == null || name.trim().isEmpty()) {
            throw new NameException("用户名不能为空！");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new PasswordException("密码不能为空！");
        }
        // 获取用户信息，比较密码
        User user = userDao.findUserByName(name);
        if (user == null) {
            throw new NameException("用户不存在");
        }
        String md5 = DigestUtils.md5Hex(password + "saonima!");
        if (md5.equals(user.getPassword())) {
            return user;// 登录成功
        }
        throw new PasswordException("密码错！");
    }

    @Override
    public User regist(String name, String password, String confirm) throws UserExistException, NameException, PasswordException {
        String reg = "^\\w{3,10}$";
        if (name == null || name.trim().isEmpty()) {
            throw new NameException("不能空");
        }
        if (!name.matches(reg)) {
            throw new NameException("3~10字符");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new PasswordException("不能空");
        }
        if (!password.matches(reg)) {
            throw new PasswordException("3~10字符");
        }
        if (!password.equals(confirm)) {
            throw new PasswordException("确认密码是否一致");
        }
        // 检查用户是否已经注册！
        User user = userDao.findUserByName(name);
        if (user != null) {
            throw new UserExistException("已经注册");
        }
        String id = UUID.randomUUID().toString();
        String md5 = DigestUtils.md5Hex(password + "saonima!");
        user = new User(id, name, md5);
        int i = userDao.saveUser(user);
        if (i != 1) {
            throw new RuntimeException("保存失败");
        }
        return user;
    }
}
