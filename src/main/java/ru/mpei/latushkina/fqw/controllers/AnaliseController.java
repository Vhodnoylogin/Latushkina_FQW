package ru.mpei.latushkina.fqw.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mpei.latushkina.fqw.models.dto.FaultData;
import ru.mpei.latushkina.fqw.services.AnaliseService;

@Slf4j
@RestController
@RequestMapping("/data/analise")
public class AnaliseController {

    private final AnaliseService analiseService;

    @Autowired
    public AnaliseController(AnaliseService analiseService) {
        this.analiseService = analiseService;
    }

    @GetMapping("/{id}")
    public FaultData analiseMeas(@PathVariable Long id,
                                 @RequestParam String phAName,
                                 @RequestParam String phBName,
                                 @RequestParam String phCName,
                                 @RequestParam(required = false) Double stock) {
        if (stock==null) {
            return analiseService.analiseMeas(id, phAName, phBName, phCName);
        } else {
            return analiseService.analiseMeas(id, phAName, phBName, phCName, stock);
        }
    }

}
