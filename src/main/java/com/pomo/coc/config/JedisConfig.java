package com.pomo.coc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis配置类
 */
@Configuration
public class JedisConfig {
    @Value("${spring.redis.single}")
    private String strSingleNode;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private Integer maxAWait;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Value("${spring.redis.password}")
    private String password;

    /**
     * jedis配置
     *
     * @return
     */
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle); //最大空闲数
        config.setMaxWaitMillis(maxAWait); //最大等待时间
        config.setMaxTotal(maxActive); //最大连接数
        return config;
    }

    /**
     * 获取jedispool
     *
     * @return
     */
    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig config = getJedisPoolConfig();
        System.out.println("strSingleNode:" + this.strSingleNode);
        String[] nodeArr = this.strSingleNode.split(":");

        JedisPool jedisPool = null;
        if (this.password.isEmpty()) {
            jedisPool = new JedisPool(
                    config,
                    nodeArr[0],
                    Integer.valueOf(nodeArr[1]),
                    this.timeout);
        } else {
            jedisPool = new JedisPool(
                    config,
                    nodeArr[0],
                    Integer.valueOf(nodeArr[1]),
                    this.timeout,
                    this.password);
        }
        return jedisPool;
    }
}
