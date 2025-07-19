package ru.homework.synthetic_human_core_starter.droidProcessing;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.homework.synthetic_human_core_starter.command.Command;
import ru.homework.synthetic_human_core_starter.command.CommandLevel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public abstract class DroidService {
    private static final Logger logger = LoggerFactory.getLogger(DroidService.class);
    private final ThreadPoolExecutor threadPoolExecutor;
    private final MeterRegistry meterRegistry;

    public DroidService(ThreadPoolExecutor threadPoolExecutor, MeterRegistry meterRegistry) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.meterRegistry = meterRegistry;
    }

    public final void processCommand(Command command) throws RejectedExecutionException {
        if (isCritical(command)) {
            processCriticalCommand(command);
        } else {
            processNonCriticalCommand(command);
            postProcess(command);
        }
    }

    protected boolean isCritical(Command command) {
        return command.getCommandLevel().equals(CommandLevel.CRITICAL);
    }

    // Обработка критичной команды (можно переопределить)
    protected void processCriticalCommand(Command command) {
        logger.info(command.toString());
    }

    // Обработка не критичной команды (можно переопределить)
    protected void processNonCriticalCommand(Command command) {
        threadPoolExecutor.submit(() -> logger.info(command.toString()));
    }

    // Пост-обработка (например, метрики) — можно дополнять
    protected void postProcess(Command command) {
        meterRegistry.counter("commands.completed", "author", command.getAuthor())
                .increment();
        meterRegistry.gauge("commands.queue.size", threadPoolExecutor.getQueue(), BlockingQueue::size);
    }

}
