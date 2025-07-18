package ru.homework.synthetic_human_core_starter.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.homework.synthetic_human_core_starter.annotations.WeylandWatchingYou;

@Aspect
@Component
public class WeylandAspect {

    private static final Logger logger = LoggerFactory.getLogger(WeylandAspect.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public WeylandAspect(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Around("@annotation(weylandWatchingYou)")
    public Object logMethod(ProceedingJoinPoint joinPoint, WeylandWatchingYou weylandWatchingYou) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String topic = weylandWatchingYou.kafkaTopic();

        logger.info("ENTER: {}", methodName);
        if (!topic.isEmpty()) {
            kafkaTemplate.send(topic, "ENTER: " + methodName);
        }

        try {
            Object result = joinPoint.proceed();

            logger.info("EXIT: {} with result {}", methodName, result);
            if (!topic.isEmpty()) {
                kafkaTemplate.send(topic, "EXIT: " + methodName + " with result " + result);
            }
            return result;
        } catch (Throwable ex) {
            logger.error("EXCEPTION in {}: {}", methodName, ex.getMessage());
            if (!topic.isEmpty()) {
                kafkaTemplate.send(topic, "EXCEPTION in " + methodName + ": " + ex.getMessage());
            }
            throw ex;
        }
    }
}
