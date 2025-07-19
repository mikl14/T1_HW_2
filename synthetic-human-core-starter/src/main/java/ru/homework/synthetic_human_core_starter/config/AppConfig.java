package ru.homework.synthetic_human_core_starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import ru.homework.synthetic_human_core_starter.aop.WeylandAspect;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@AutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {

    @Bean
    @Scope("singleton")
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(MeterRegistry meterRegistry) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                4, 8, 30, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.AbortPolicy());

        ExecutorServiceMetrics.monitor(meterRegistry, threadPoolExecutor, "custom.thread-pool", Tags.empty());

        return threadPoolExecutor;
    }

    @Bean
    public WeylandAspect weylandAspect(KafkaTemplate<String, String> kafkaTemplate) {
        return new WeylandAspect(kafkaTemplate);
    }
}
