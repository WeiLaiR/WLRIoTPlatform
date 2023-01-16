package com.wei.iotplatformuserservice.utils;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private static long time = new Date().getTime();
    //    计数位
    private final AtomicLong count = new AtomicLong(0);

    //      机器码,通过配置文件配置。
    @Value("${machine.code}")
    public static long mac;

    @Override
    public Long nextId(Object entity) {
        long now;

        if (count.get() < 99) {
            count.addAndGet(1);
            now = time;
        } else {
            now = new Date().getTime();
//            这里仅锁住对象即可，没有必要锁住整个类
            synchronized (this) {
                if (now == time) {
                    do {
                        now = new Date().getTime();
                    } while (now == time);
                    time = now;
                }
            }
            count.set(0);
        }

//        乘10默认机器码一位十进制，可设置0-9
        now = now * 10 + mac;
//        乘100默认计数位两位十进制，可设置0-99
        now = now * 100 + count.get();
        //返回生成的id值即可.
        return now;
    }
}
