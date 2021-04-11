package com.cxy.shiro.controller;

import com.cxy.shiro.mapper.UsersMapper;
import com.cxy.shiro.pojo.Users;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
public class MyController {
    @Autowired
    UsersMapper usersMapper;
    @RequestMapping({"/","/h1"})
    public String h1(Model model){
        model.addAttribute("msg","hello shiro");
        return "index";
    }
    @RequestMapping("/add")
    public String add(){
        return "user/add";
    }
    @RequestMapping("/update")
    public String update(){
        return "user/update";
    }
    @RequestMapping("/select")
    public String select(){
        return "user/select";
    }
    @RequestMapping("/getlist")
    public String getlist(String userName,String userPhone,String userHouse,String userEmil,Model model){
        //收集数据
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("userName",userName);
        map.put("userPhone",userPhone);
        map.put("userHouse",userHouse);
        map.put("userEmil",userEmil);
        if(userName!=null&&map.get("userName").equals(""))
            map.put("userName",null);
        if(userPhone!=null&&map.get("userPhone").equals(""))
            map.put("userPhone",null);
        if(userHouse!=null&&map.get("userHouse").equals(""))
            map.put("userHouse",null);
        if(userEmil!=null&&map.get("userEmil").equals(""))
            map.put("userEmil",null);
        //查询
        List<Users> users = usersMapper.getUsers(map);
        model.addAttribute("users",users);
        return "user/list";
    }
    @RequestMapping("/login")
    public String lo(){
        return "login";
    }
    @RequestMapping("/islogin")
    public String login(Model model,String nums,String pwd){
        //获取用户
        Subject subject = SecurityUtils.getSubject();
        //封装
        UsernamePasswordToken token = new UsernamePasswordToken(nums,pwd);
        //执行登录的方法
        try{
            subject.login(token);
            //通过subject取
            String num1 = (String) subject.getPrincipal();
            Session session = subject.getSession();
            session.setAttribute("author",pwd);
            model.addAttribute("user",num1);
            model.addAttribute("msg","hello shiro");
            return "index";
        }catch (UnknownAccountException uae) {
            model.addAttribute("msg","没有用户名为-->" + token.getPrincipal());
            return "login";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg","密码帐户-->" + token.getPrincipal() + ",是不正确的!");
            return "login";
        } catch (LockedAccountException lae) {
            model.addAttribute("msg","用户名的帐户-->" + token.getPrincipal() + ",被锁定了.请联系您的管理员解锁.");
            return "login";
        }
    }
    @RequestMapping("/noauthor")
    public String noauthor(){
        return "err/err";
    }
    @RequestMapping("/loginout")
    public String loginout(Model model){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg","hello shiro");
        return "index";
    }
    @RequestMapping("/addUser")
    public String AddUser(String userPhone ,String userName,String pwd,String useremil,String userhouse,Model model){
        if(userPhone==null&&userName==null&&pwd==null){
            model.addAttribute("msg","故障了!!!err!!!请重试!");
            return "user/list";
        }else{
            Map<String,String> map = new LinkedHashMap<>();
            if(userPhone.equals("")||userName.equals("")||pwd.equals("")){
                model.addAttribute("msg","关键信息没填完哦");
                return "user/list";
            }else{
                map.put("userPhone",userPhone);
                map.put("pwd",pwd);
                map.put("userName",userName);
            }
            if(useremil!=null&&!useremil.equals("")){
                map.put("userEmil",useremil);
            }else{
                map.put("userEmil",null);
            }
            if(userhouse!=null&&!userhouse.equals("")){
                map.put("userHouse",userhouse);
            }else{
                map.put("userHouse",null);
            }
            try{
                int i = usersMapper.addUser(map);
                model.addAttribute("msg","添加成功!");
                List<Users> users = usersMapper.getAllUsers();
                model.addAttribute("users",users);
                return "user/list";
            }catch (Exception e){
                System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            model.addAttribute("msg","数据格式出错!");
            List<Users> users = usersMapper.getAllUsers();
            model.addAttribute("users",users);
            return "user/list";
        }
    }
    @RequestMapping("/updateUser")
    public String UpdateUser(String userPhone ,String userName,String pwd,String userEmil,String userhouse,Model model){
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if(userName!=null&&userName.equals(""))
            map.put("userName",null);
        else
            map.put("userName",userName);
        if(pwd!=null&&pwd.equals(""))
            map.put("pwd",null);
        else
            map.put("pwd",pwd);
        if(userEmil!=null&&userEmil.equals(""))
            map.put("userEmil",null);
        else
            map.put("userEmil",userEmil);
        if(userhouse!=null&&userhouse.equals(""))
            map.put("userHouse",null);
        else
            map.put("userHouse",userhouse);
        if(userPhone!=null&&userPhone.equals("")){
            List<Users> users = usersMapper.getAllUsers();
            model.addAttribute("msg","修改的手机号不存在");
            model.addAttribute("users",users);
            return "user/list";
        }
        else{
            map.put("userPhone",userPhone);
            System.out.println("----------------------------------------------------");
            System.out.println(map);
            System.out.println("----------------------------------------------------");
            map.put("userhouse",userhouse);
            int i = usersMapper.updateUsers(map);
            System.out.println(i);
            System.out.println("----------------------------------------------------");
            if(i!=0){
                System.out.println("====================================================");
                System.out.println("修改成功!");
                System.out.println("====================================================");
                List<Users> users = usersMapper.getAllUsers();
                model.addAttribute("users",users);
                System.out.println("====================================================");
                return "user/list";
            }else{
                System.out.println("----------------------------------------------------");
                List<Users> users = usersMapper.getAllUsers();
                model.addAttribute("users",users);
                model.addAttribute("msg","修改的手机号不存在");
                System.out.println("----------------------------------------------------");
                return "user/list";
            }

        }
    }

}
