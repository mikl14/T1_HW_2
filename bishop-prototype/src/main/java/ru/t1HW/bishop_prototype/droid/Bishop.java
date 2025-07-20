package ru.t1HW.bishop_prototype.droid;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.homework.synthetic_human_core_starter.annotations.WeylandWatchingYou;
import ru.homework.synthetic_human_core_starter.command.Command;
import ru.homework.synthetic_human_core_starter.droidProcessing.DroidService;

import java.util.concurrent.ThreadPoolExecutor;

@Service
public class Bishop extends DroidService {

    private static final Logger logger = LoggerFactory.getLogger(DroidService.class);

    public Bishop(ThreadPoolExecutor threadPoolExecutor, MeterRegistry meterRegistry) {
        super(threadPoolExecutor, meterRegistry);
    }

    /**
     * <b>processCriticalCommand</b> - переопределяет выполнение критической команды, аннотация отправляет логи в kafka топик
     *
     * @param command
     */
    @Override
    @WeylandWatchingYou(kafkaTopic = "droidTopic",logToTerminal = false)
    public void processCriticalCommand(Command command) {
        logger.info("Бишоп выполняет критически важную задачу: ! " + command.toString());
    }

    /**
     * <b>processNonCriticalCommand</b> - переопределяет выполнение обычных команд
     *
     * @param command
     */
    @Override
    public void processNonCriticalCommand(Command command) {
        logger.info("Бишоп выполняет обычную задачу: ! " + command.toString());
    }
}
