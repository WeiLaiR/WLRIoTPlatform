package com.wei.iotplatformuserservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformuserservice.exception.CustomException;
import com.wei.iotplatformuserservice.mapper.LoginMapper;
import com.wei.iotplatformuserservice.pojo.Login;
import com.wei.iotplatformuserservice.service.LoginService;
import com.wei.iotplatformuserservice.utils.CreateRsaKey;
import com.wei.iotplatformuserservice.utils.RSAUtils;
import com.wei.iotplatformuserservice.utils.RedisUtil;
import com.wei.iotplatformuserservice.utils.TokenUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements LoginService {

    //    设置token过期时间，单位为秒，当前为3天
    private static final long TIMEOUT = 259200;

    private LoginMapper loginMapper;

    @Autowired
    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    private CreateRsaKey createRsaKey;

    @Autowired
    public void setCreateRsaKey(CreateRsaKey createRsaKey) {
        this.createRsaKey = createRsaKey;
    }

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public Map<String, Object> signIn(String email, String password) {
    //        获取私钥
        String privateKeyString = createRsaKey.getPrivateKey();
        RSAPrivateKey privateKey;
        try {
            privateKey = RSAUtils.getPrivateKey(privateKeyString);
        }catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomException(400, "解密发生未知错误!");
        }
        String pw = RSAUtils.privateDecrypt(password, privateKey);

        Map<String, Object> map = new HashMap<>();
        Login login = loginMapper.queryLogin(email);
        if (!Objects.isNull(login) && StringUtils.hasText(login.getPassword())) {
            if (login.getStatus() != 0) {
                if (login.getPassword().equals(DigestUtils.sha256Hex(pw.concat(email)))) {
//                生成Token并存入redis
                    String token = TokenUtils.getToken(login.getUid(), email);
                    redisUtil.set(login.getUid().toString(), token, TIMEOUT);

                    map.put("token", token);
                    map.put("status", 200);
                    map.put("message", "登录成功!");
                } else {
                    map.put("status", 400);
                    map.put("message", "密码错误!");
                }
            }else {
                map.put("status", 400);
                map.put("message", "用户已被禁用,请及时联系管理员。");
            }
        } else {
            map.put("status", 400);
            map.put("message", "用户不存在!");
        }

        return map;
    }

    @Override
    public Map<String, Object> logOut() {
        Long id = TokenUtils.getId();
        if (id == null) {
            throw new CustomException(400, "token已过期!");
        }
        String str = id.toString();
        Map<String, Object> map = new HashMap<>();
        redisUtil.del(str);
        if (redisUtil.get(str) == null) {
            map.put("status", 200);
            map.put("message", "注销登录成功!");
        } else {
            map.put("status", 400);
            map.put("message", "注销登录失败，请再次尝试!");
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteLoginInfo(Long uid) {
        Map<String, Object> map = new HashMap<>();
        if (loginMapper.deleteById(uid) > 0) {
            map.put("status", 200);
            map.put("message", "删除成功");
        } else {
            map.put("status", 400);
            map.put("message", "删除失败");
        }
        return map;
    }

    @Override
    public Map<String, Object> register(String email, String password, String vCode){

        //        获取私钥
        String privateKeyString = createRsaKey.getPrivateKey();
        RSAPrivateKey privateKey;
        try {
            privateKey = RSAUtils.getPrivateKey(privateKeyString);
        }catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomException(400, "解密发生未知错误!");
        }
        String pw = RSAUtils.privateDecrypt(password, privateKey);

        System.out.println(pw);


        String s = loginMapper.queryEmailEmpty(email);

        if (StringUtils.hasText(s)) {
            throw new CustomException(400, "邮箱已被注册!");
        }

        HashMap<String, Object> map = new HashMap<>();


//      校验验证码
        String vCode1 = redisUtil.get(email.concat("vCode"));

        if (StringUtils.hasText(vCode1) && vCode1.equals(vCode)) {
            Login login = new Login();

            login.setEmail(email);
            login.setPassword(DigestUtils.sha256Hex(pw.concat(email)));

            if (loginMapper.insert(login) > 0) {
                map.put("status", 200);
                map.put("message", "注册成功!");
            } else {
                map.put("status", 400);
                map.put("message", "注册失败!");
            }
        } else {
            throw new CustomException(400, "验证码错误!");
        }


        return map;
    }

    @Override
    public Map<String, Object> queryStatusZeroList() {
        HashMap<String, Object> map = new HashMap<>();
        List<Login> logins;

        try {
            logins = loginMapper.queryStatusZeroList();
        }catch (Exception e) {
            throw new CustomException(400, "查询失败!");
        }

        map.put("status", 200);
        map.put("message", "查询成功!");
        map.put("data", logins);

        return map;
    }

    @Override
    public Map<String, Object> updateStatus(Long uid) {
        Map<String, Object> map = new HashMap<>();

        Integer status;

        try {
            status = loginMapper.queryStatus(uid);
        } catch (Exception e) {
            throw new CustomException(400, "出现未知异常!(UserStatus)");
        }

        Login login = new Login();
        login.setUid(uid);
        if (status == 0) {
            login.setStatus(1);
            map.put("message", "该账号状态已恢复正常!");
        } else {
            login.setStatus(0);
            map.put("message", "该账号已被封禁 禁止登录!");
        }
        try {
            if (loginMapper.updateById(login) > 0) {
                map.put("status", 200);
            } else {
                map.put("status", 400);
                map.put("message", "修改失败!");
            }
        } catch (Exception e) {
            throw new CustomException(400, "出现未知错误!");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryStatus(Long uid) {
        Integer status;
        try {
            status = loginMapper.queryStatus(uid);
        } catch (Exception e) {
            throw new CustomException(400, "出现未知异常!(UserStatus)");
        }
        Map<String, Object> map = new HashMap<>();
        if (status != null) {
            map.put("status", 200);
            map.put("message", "查询成功!");
            map.put("data", status);
        }else {
            map.put("status", 400);
            map.put("message", "查询失败!");
        }
        return map;
    }

    @Override
    public Map<String, Object> verification(String password) {
        String privateKeyString = createRsaKey.getPrivateKey();
        RSAPrivateKey privateKey;
        try {
            privateKey = RSAUtils.getPrivateKey(privateKeyString);
        }catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomException(400, "解密发生未知错误!");
        }
        String pw = RSAUtils.privateDecrypt(password, privateKey);

        Map<String, Object> map = new HashMap<>();
        Long id = TokenUtils.getId();
        if (id == null) {
            throw new CustomException(401, "请先登录!");
        }
        Login login = loginMapper.selectById(id);
        if (!Objects.isNull(login) && StringUtils.hasText(login.getPassword())) {
            if (login.getStatus() != 0) {
                if (login.getPassword().equals(DigestUtils.sha256Hex(pw.concat(login.getEmail())))) {

                    map.put("status", 200);
                    map.put("message", "验证成功!");
                } else {
                    map.put("status", 401);
                    map.put("message", "密码错误!");
                }
            }else {
                map.put("status", 401);
                map.put("message", "用户已被禁用,请及时联系管理员。");
            }
        } else {
            map.put("status", 401);
            map.put("message", "用户不存在!");
        }

        return map;
    }


}
