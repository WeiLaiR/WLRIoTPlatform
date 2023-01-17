package com.wei.iotplatformgateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.wei.iotplatformgateway.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;


@Order(-1)
@Component
public class GatewayFilter implements GlobalFilter {


    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    @Value("${filter.config.excludeUrls}")
    private String excludeUrls; // 获取配置文件中不需要过滤的uri

    private List<String> excludes;


    /**
     * 过滤器，主要用于校验Token是否合法
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return a {@code Publisher} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 初始化
        // 移除配置文件中不过滤url，字符串的前空白和尾部空白
        excludes = Splitter.on(",").trimResults().splitToList(this.excludeUrls);


        ServerHttpRequest request = exchange.getRequest();
        String uri = String.valueOf(request.getURI());

        if (isExcludesUrl(uri)){
            return chain.filter(exchange);
        }

//        获取token
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("token");

//        查看token是否为空
        if (!StringUtils.hasText(token)) {
            System.out.println("========================token是否为空==========================");
            try {
                return generateJSON(exchange,400,"信息异常，请重新登录！");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //      校验token是否能正常解析
        String eid;
        try {
            eid = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException jwtDecodeException){
            System.out.println("========================解析token==========================");
            try {
                return generateJSON(exchange,400,"信息异常，请重新登录！");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //      是否在redis中有记录
        String storedToken = redisUtil.get(eid);
        if (!StringUtils.hasText(storedToken)) {
            System.out.println("========================在redis查询==========================");
            try {
                return generateJSON(exchange,400,"信息异常，请重新登录！");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //      对比token是否一致
        if (!storedToken.equals(token)) {
            System.out.println("========================对比token==========================");
            try {
                return generateJSON(exchange,400,"信息异常，请重新登录！");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return chain.filter(exchange);
    }

    /**
     * 由于自定义异常不能在filter生效，故使用此方法模拟自定义异常
     * @param exchange ServerWebExchange
     * @param status 状态码
     * @param message 消息
     * @return Mono<Void>
     * @param <T> 泛型
     * @throws JsonProcessingException JsonProcessingException
     */
    private <T>Mono generateJSON(ServerWebExchange exchange, Object status, String message) throws JsonProcessingException {
        ServerHttpResponse response = exchange.getResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<>();
        map.put("status",status);
        map.put("message",message);
        String json = objectMapper.writeValueAsString(map);
        byte[] bits = json.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
//        返回的状态码，由于前端原因，所以这里返回200
        response.setStatusCode(HttpStatus.OK);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 判断访问路径是否需要过滤
     * @param path 路径
     * @return true 不需要过滤，false 需要过滤
     */

    private boolean isExcludesUrl(String path) {
        for (String v : this.excludes) {
            if (path.contains(v)) {// 判断请求uri 是否满足配置文件uri要求
                return true;  // 满足、也就是请求uri 为 登录、注册，返回true
            }
        }
        return false; // 不满足、也就是请求uri 不是登录、注册，返回false
    }
}
