package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {

        Map<String, String> map = new HashMap<>();
        if(username == null) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if(password == null || confirmedPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }
        username = username.trim();

        if(username.length() == 0) {
            map.put("error_message", "用户名不能为空");
            return map;
        }

        if(password.length() == 0 || confirmedPassword.length() == 0) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        if(username.length() > 100) {
            map.put("error_message", "用户名过长");
            return map;
        }
        if(password.length() > 100 || confirmedPassword.length() > 100){
            map.put("error_message", "密码过长");
            return map;
        }
        if(!password.equals(confirmedPassword)) {
            map.put("error_message", "两次输入密码不一致");
            return map;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        List<User> userList = userMapper.selectList(queryWrapper);
        if(!userList.isEmpty()) {
            map.put("error_message", "用户名已存在");
            return map;
        }
        String encoded_password = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/76795_lg_5b565e51a2.jpg";
        User user = new User(null, username, encoded_password, photo);
        userMapper.insert(user);
        map.put("error_message", "success");
        return map;
    }
}
