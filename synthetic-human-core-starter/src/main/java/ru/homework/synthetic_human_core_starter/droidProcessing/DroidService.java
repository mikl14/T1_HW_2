package ru.homework.synthetic_human_core_starter.droidProcessing;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.homework.synthetic_human_core_starter.annotations.WeylandWatchingYou;
import ru.homework.synthetic_human_core_starter.command.Command;
import ru.homework.synthetic_human_core_starter.command.CommandLevel;

import java.util.concurrent.*;


@Service
public class DroidService {

    private static final Logger logger = LoggerFactory.getLogger(DroidService.class);
    private final ThreadPoolExecutor threadPoolExecutor;
    private final MeterRegistry meterRegistry;
    public DroidService(ThreadPoolExecutor threadPoolExecutor, MeterRegistry meterRegistry) {

        this.threadPoolExecutor = threadPoolExecutor;
        this.meterRegistry = meterRegistry;
    }


    public void processCommand(Command command) throws RejectedExecutionException{
        if (command.getCommandLevel().equals(CommandLevel.CRITICAL)) {
            logger.info(command.toString());
        } else {
            threadPoolExecutor.submit(() -> logger.info(command.toString()));

            meterRegistry.counter("commands.completed", "author", command.getAuthor())
                    .increment();

            meterRegistry.gauge("commands.queue.size", threadPoolExecutor.getQueue(), BlockingQueue::size);

        }
    }

}
