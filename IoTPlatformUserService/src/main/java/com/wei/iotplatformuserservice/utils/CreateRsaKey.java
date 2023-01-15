package com.wei.iotplatformuserservice.utils;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreateRsaKey implements ApplicationRunner {

    private static String publicKey;  //公钥
    private static String privateKey; //私钥

    /**
     * 用于密钥生成
     * 每小时定时更新密钥
     * 经过我不科学的计算，本机生成一次密钥大约10ms
     * 遂我认为我们可以大幅提高密钥更新的频率进而提高安全性。
     * 在本机这种理想环境，每秒更换一次密钥基本不会对登录注册产生任何影响。
     * 实际请考虑网络延迟、服务端性能、客户端性能等复杂因素的影响
     */
//    @PostConstruct
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void createKey() {
        Map<String, String> keys = RSAUtils.createKeys(1024);
        publicKey = keys.get("publicKey");
        privateKey = keys.get("privateKey");
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * 项目启动后自动生成密钥
     *
     * @param args 1
     */

    @Override
    public void run(ApplicationArguments args)  {
        Map<String, String> keys = RSAUtils.createKeys(1024);
        publicKey = keys.get("publicKey");
        privateKey = keys.get("privateKey");
    }
}
