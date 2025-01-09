package egovframework.com.cop.bbs.bbs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import egovframework.com.cop.bbs.bbs.config.RabbitMQConfig;
import egovframework.com.cop.bbs.bbs.event.BoardEvent;

@Service
@RequiredArgsConstructor
public class BoardEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publishBoardEvent(BoardEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,
            "bbs.event." + event.getEventType().toString().toLowerCase(),
            event
        );
    }
}
