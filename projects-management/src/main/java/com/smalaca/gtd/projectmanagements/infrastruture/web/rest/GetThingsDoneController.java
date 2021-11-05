package com.smalaca.gtd.projectmanagements.infrastruture.web.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get-things-done")
public class GetThingsDoneController {
    @GetMapping
    public String getThingsDone(@RequestParam(defaultValue = "No Name") String name) {
        return name + "! Get all the things done!";
    }
}
