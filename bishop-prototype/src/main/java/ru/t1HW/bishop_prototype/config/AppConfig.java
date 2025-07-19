package ru.t1HW.bishop_prototype.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import ru.homework.synthetic_human_core_starter.droidProcessing.DroidService;
import ru.t1HW.bishop_prototype.droid.Bishop;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAspectJAutoProxy

public class AppConfig {

    @Bean
    @Scope("singleton")
    public DroidService droidService(ThreadPoolExecutor threadPoolExecutor, MeterRegistry meterRegistry) {
        return new Bishop(threadPoolExecutor,meterRegistry);
    }
}
