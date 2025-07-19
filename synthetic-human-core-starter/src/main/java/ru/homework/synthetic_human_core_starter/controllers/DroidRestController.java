package ru.homework.synthetic_human_core_starter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.homework.synthetic_human_core_starter.annotations.WeylandWatchingYou;
import ru.homework.synthetic_human_core_starter.command.Command;
import ru.homework.synthetic_human_core_starter.droidProcessing.DroidService;
import ru.homework.synthetic_human_core_starter.exceptions.NoValidCommandException;

import java.util.concurrent.RejectedExecutionException;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/droid/api")

public abstract class DroidRestController {
    private final ObjectMapper mapper;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DroidRestController.class);
    private final DroidService droidService;


    public DroidRestController(ObjectMapper mapper, DroidService droidService) {
        this.mapper = mapper;
        this.droidService = droidService;
    }

    protected Command validateCommand(String bodyCommand) throws NoValidCommandException {
        try {
            return mapper.readValue(bodyCommand, Command.class);
        } catch (Exception e) {
            throw new NoValidCommandException("Команда не соответствует формату! " + e);
        }
    }

    @PostMapping("/doCommand")
    public ResponseEntity<String> doCommand(@RequestBody String bodyCommand) {
        try {
            Command command = validateCommand(bodyCommand);
            droidService.processCommand(command);
            return ResponseEntity.ok("Command processed successfully");

        } catch (NoValidCommandException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Invalid command: " + e.getMessage());
        } catch (RejectedExecutionException e) {
            logger.error("Очередь переполнена, задача отклонена: ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Droid is too busy: " + e.getMessage());
        } catch (Exception e) {
            // Общий catch для других ошибок
            logger.error("Unexpected error processing command", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal droid error");
        }
    }
}
