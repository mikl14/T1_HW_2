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

    /**
     * <b>processCommand</b> - метод выполнения команды с проверкой на тип команды
     * @param command
     * @throws RejectedExecutionException
     */
    public final void processCommand(Command command) throws RejectedExecutionException {
        if (isCritical(command)) {
            processCriticalCommand(command);
        } else {
            processNonCriticalCommand(command);
        }
        postProcess(command);
    }

    protected boolean isCritical(Command command) {
        return command.getCommandLevel().equals(CommandLevel.CRITICAL);
    }

    /**
     * <b>processCriticalCommand</b> - метод обработки critical команд (рассчитанно что будет переопределен наследниками)
     * @param command
     */
    protected void processCriticalCommand(Command command) {
        logger.info(command.toString());
    }

    protected void processNonCriticalCommand(Command command) {
        threadPoolExecutor.submit(() -> logger.info(command.toString()));
    }

    /**
     * <b>postProcess</b> - метод постобработки команд по умолчанию в нем метрики
     * @param command
     */
    protected void postProcess(Command command) {
        meterRegistry.counter("commands.completed", "author", command.getAuthor())
                .increment();
        if(!isCritical(command)) {
            meterRegistry.gauge("commands.queue.size", threadPoolExecutor.getQueue(), BlockingQueue::size);
        }
    }
}
