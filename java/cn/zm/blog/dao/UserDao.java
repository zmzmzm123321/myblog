package cn.zm.blog.dao;

import cn.zm.blog.entity.User;

/**
 * 用户信息访问层
 * @author TM
 *
 */
public interface UserDao {
	/**
	 * 根据用户名查找用户信息
	 * 
	 * @param name
	 *            用户名
	 * @return 如果找到返回用户信息，否则正确返回null
	 */
	User findUserByName(String name);
	
	/**
	 * 将用户数据保存到数据库中
	 * @param user 被保存的用户信息
	 * @return 更新数据行数，返回1表示更新成功
	 */
	int saveUser(User user);
	
	User findUserById(String userId);
}
