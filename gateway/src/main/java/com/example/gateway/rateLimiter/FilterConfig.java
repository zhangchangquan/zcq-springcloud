package com.example.gateway.rateLimiter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Configuration
@PropertySource("classpath:jwt.properties")
public class FilterConfig implements GlobalFilter, Ordered {

    @Value(value = "#{'${jwt.ignoreUrlList}'.split(',')}")
    private List<String> pathList;

    private String name;

    public static void main(String[] args) {
        System.out.println("hello");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        RequestPath path = request.getPath();
        if(pathList.contains(path.toString())){
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("token");
        ServerHttpResponse response = exchange.getResponse();
        if(StringUtils.isEmpty(token)){
            return writeWith(response,"用户未登录");
        }
        Claims claims = Jwts.parser().setSigningKey("jwttest").parseClaimsJws(token).getBody();
        if(claims == null){
            return writeWith(response,"用户登录信息失效");
        }
        return chain.filter(exchange);
    }

    private Mono<Void> writeWith(ServerHttpResponse response,String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",message);
        byte[] bytes = jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
