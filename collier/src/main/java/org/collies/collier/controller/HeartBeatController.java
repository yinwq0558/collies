package org.collies.collier.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HeartBeatController {

    @RequestMapping(value = "/heartbeat", method = RequestMethod.POST)
    public Map heartBeat(@RequestBody Map data) {
        System.out.println(data);
        return data;
    }

}
