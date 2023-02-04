package com.wei.iotplatformuserservice.controller;


import com.wei.iotplatformuserservice.utils.CreateRsaKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/ras")
public class RsaController {
    private CreateRsaKey rsa;

    @Autowired
    public void setRsa(CreateRsaKey rsa) {
        this.rsa = rsa;
    }


    /**
     * 获取公钥
     * @return 公钥
     */
    @GetMapping("/getPublicKey")
    public Map<String, Object> getPublicKey() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("publicKey", rsa.getPublicKey());
        return map;
    }


}
