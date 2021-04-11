package com.cxy.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
/**
 * Simple com.cxy.shiro.Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
@Slf4j
public class Quickstart {
    public static void main(String[] args) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        securityManager.setRealm(iniRealm);

        SecurityUtils.setSecurityManager(securityManager);
        //获取当前对象
        Subject currentUser = SecurityUtils.getSubject();
        //通过当前对象,拿到session
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Subject--->session[" + value + "]");
        }
        //判断当前用户是否被认证
        if (!currentUser.isAuthenticated()) {
            //没被认证的话生成一个令牌
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);//记住我
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("没有用户名为-->" + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("密码帐户-->" + token.getPrincipal() + ",是不正确的!");
            } catch (LockedAccountException lae) {
                log.info("用户名的帐户-->" + token.getPrincipal() + ",被锁定了.  " +
                        "请联系您的管理员解锁.");
            }
            catch (AuthenticationException ae) {
                //其他的catch块
            }
        }

        //say who they are:
        //打印它们的标识主体(在本例中是用户名):
        log.info("User [" + currentUser.getPrincipal() + "] 登陆成功.");

        //test a role:
        if (currentUser.hasRole("schwartz")) {
            log.info("愿schwartz与你同在!");
        } else {
            log.info("你好,凡人.");
        }

        //测试类型化权限(不是实例级权限)
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("你可以使用lightsaber。明智地使用它.");
        } else {
            log.info("抱歉，lightsaber只对schwartz大师开放.");
        }

        //一个(非常强大的)实例级权限:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("你可以用车牌(id)“驾驶”温尼贝戈“eagle5”。  " +
                    "钥匙在这里——玩得开心!");
        } else {
            log.info("对不起，你不能开鹰5型温尼贝戈!");
        }

        //全部完成-注销!
        currentUser.logout();

        System.exit(0);
    }
}