package com.zmz.app.api.controller;

import com.zmz.app.application.service.ProgrammerService;
import com.zmz.app.domain.model.ProgrammerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-28 20:55
 */
@RestController
public class ProgrammerController {

    @Autowired
    private ProgrammerService programmerService;

    @GetMapping("/programmers")
    public List<ProgrammerModel> get() {
        return programmerService.selectAll();
    }
}
