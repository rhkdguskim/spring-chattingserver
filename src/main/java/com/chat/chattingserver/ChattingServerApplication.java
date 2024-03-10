package com.chat.chattingserver;

import com.chat.chattingserver.common.util.LogTrace;
import com.chat.chattingserver.common.util.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChattingServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChattingServerApplication.class, args);

    }

    @Bean
    public LogTrace logTrace()
    {
        return new ThreadLocalLogTrace();
    }
}
