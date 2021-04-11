package com.cxy.shiro;

import com.cxy.shiro.mapper.UsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootTest
class ShiroApplicationTests {
    @Autowired
    UsersMapper usersMapper;
    @Test
    void contextLoads() {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("userName","blank");
        map.put("userPhone",null);
        map.put("userHouse",null);
        map.put("userEmil",null);
        System.out.println(usersMapper.getUsers(map));

    }
    @Test
    void t1(){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("userName","blank");
        map.put("pwd",null);
        map.put("userHouse",null);
        map.put("userEmil",null);
        map.put("userPhone","");
        int i = usersMapper.updateUsers(map);
        System.out.println(i);
    }
    @Test
    void t2(){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("userPhone","13811112222");
        map.put("pwd","123");
        map.put("userName","测试新用户");
        map.put("userEmil",null);
        map.put("userHouse",null);
        int i = usersMapper.addUser(map);
        System.out.println(i);
    }

}
