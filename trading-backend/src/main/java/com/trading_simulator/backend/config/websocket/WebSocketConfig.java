package com.trading_simulator.backend.config.websocket;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
// Đang gặp lỗi tại send-single, không gửi được do kết nối chập chờn giữa client - server → session thay đổi liên tục
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${POSTMAN_URL}")
    private String POSTMAN_URL;

    @Value("${VUE_URL}")
    private String VUE_URL;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/public");
//                .setHeartbeatValue(new long[]{10000, 10000});
        registry.setUserDestinationPrefix("/private");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .addInterceptors(new UserHandshakeInterceptor())
                .setAllowedOriginPatterns(POSTMAN_URL, VUE_URL)
                .withSockJS();
//                .setHeartbeatTime(10000);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.getObjectMapper().registerModule(new JavaTimeModule());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);

        return false;
    }
}
