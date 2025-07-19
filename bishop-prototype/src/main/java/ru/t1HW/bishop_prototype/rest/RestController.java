package ru.t1HW.bishop_prototype.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.homework.synthetic_human_core_starter.controllers.DroidRestController;
import ru.homework.synthetic_human_core_starter.droidProcessing.DroidService;

@org.springframework.web.bind.annotation.RestController
public class RestController extends DroidRestController {
    public RestController(ObjectMapper mapper, DroidService droidService) {
        super(mapper, droidService);
    }


}
