package com.baltan.springboot.service;

import com.baltan.springboot.bean.User;
import com.baltan.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019/1/8 16:59
 */
@Component
/**
 * 缓存的公共配置
 */
@CacheConfig(cacheNames = {"user"})
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Cacheable(cacheNames = {"allUsers"})
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Cacheable(cacheNames = {"user"}, key = "#id", condition = "#id>0", unless = "#result==null")
    /**
     * #id等同于#a0、#p0、#root.args[0]
     * #root可以获得根对象，可以实现自定义拼接key，例如：#root.method+'['+#id+']'生成key"getUserById[1]"
     * 也可以用自定义的keyGenerator生成key，@Cacheable注解中用属性keyGenerator="keyGenerator"代替"key"属性
     */
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @CachePut(cacheNames = {"user"}, key = "#user.id")
    /**
     * #user.id等同于#result.id
     */
    public User updateUser(User user) {
        userMapper.updateUser(user);
        return user;
    }

    @CacheEvict(cacheNames = {"user"}, key = "#id")
    /**
     * allEntries = true：删除缓存中的所有数据，默认为false
     * beforeInvocation = true：在方法执行前清除缓存数据，默认为false
     */
    public void deleteUserById(Integer id) {
        userMapper.deleteUserById(id);
    }

    @Caching(
            cacheable = {
                    @Cacheable(cacheNames = {"user"}, key = "#username")
            },
            put = {
                    @CachePut(cacheNames = {"user"}, key = "#result.id"),
                    @CachePut(cacheNames = {"user"}, key = "#result.name")
            }
    )
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}
