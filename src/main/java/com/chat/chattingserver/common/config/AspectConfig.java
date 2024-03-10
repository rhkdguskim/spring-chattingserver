package com.chat.chattingserver.common.config;

import com.chat.chattingserver.common.aop.annotation.LoggerAspect;
import com.chat.chattingserver.common.util.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {

    @Bean
    public LoggerAspect loggerAspect(LogTrace logTrace)
    {
        return new LoggerAspect(logTrace);
    }
}
