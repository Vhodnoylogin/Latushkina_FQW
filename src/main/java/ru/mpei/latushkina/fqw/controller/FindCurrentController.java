package ru.mpei.latushkina.fqw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mpei.latushkina.fqw.service.comtrade.CurrentService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/data/current")
public class FindCurrentController {
    private final CurrentService currentService;

    @Autowired
    public FindCurrentController(CurrentService currentService) {
        this.currentService = currentService;
    }

    @GetMapping("/sources")
    public ResponseEntity<List<String>> getAllSources() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(currentService.getAllSources());
    }

    @GetMapping("/short_circuit")
    public ResponseEntity<Map<String, List<Double>>> getShortCircuitValues(@RequestParam(defaultValue = "-1.0") double settingCurrent) {
        var listOfShortCircuit = currentService.getShortCircuit(settingCurrent);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listOfShortCircuit);
    }
}
