package com.cxy.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.shiro.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈新予
 * @since 2021-04-03
 */
@Component
public interface UsersMapper extends BaseMapper<Users> {

    List<Users> getUsers(Map<String,String> map);

    int updateUsers(Map<String,String> map);

    @Select("select * from users")
    List<Users> getAllUsers();

    @Update("update users set user_status=0 where user_phone = #{phone}")
    int KillUser(String phone);

    int addUser(Map<String,String> map);
}
