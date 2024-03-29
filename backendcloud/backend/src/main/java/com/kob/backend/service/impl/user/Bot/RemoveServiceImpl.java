package com.kob.backend.service.impl.user.Bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailImpl;
import com.kob.backend.service.user.Bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class RemoveServiceImpl implements RemoveService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        int bot_id = Integer.parseInt(data.get("bot_id"));
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        Bot bot = botMapper.selectById(bot_id);

        Map<String, String> map = new HashMap<>();
        if(bot == null) {
            map.put("error_message", "Bot不存在或已被删除");
            return map;
        }
        if(!Objects.equals(bot.getUserId(), user.getId())) {
            map.put("error_message", "没有权限删除该Bot");
            return map;
        }
        botMapper.deleteById(bot_id);
        map.put("error_message", "success");
        return map;
    }
}
