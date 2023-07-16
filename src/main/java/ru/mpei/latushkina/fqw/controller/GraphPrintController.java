package ru.mpei.latushkina.fqw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mpei.latushkina.fqw.model.dto.point.Source;
import ru.mpei.latushkina.fqw.service.graph.GraphService;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/api/v1/data/graph")
public class GraphPrintController {
    private final GraphService graphService;

    private final String HTML = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <title>Пример вставки картинки Base64</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <img src=\"data:image/png;base64,%s\" alt=\"Картинка\">\n" +
            "</body>\n" +
            "</html>";

    @Autowired
    public GraphPrintController(GraphService graphService) {
        this.graphService = graphService;
    }

//    @GetMapping("/print")
//    public ResponseEntity<String> printGraph(@RequestParam String[] sources){
//        var sourceList = Arrays.stream(sources).map(Source::new).toList();
//        var res = graphService.getGraphPointsBySource(sourceList);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(HTML.formatted(res));
//    }

    @GetMapping("/print")
    public ResponseEntity<String> printGraphByTime(
            @RequestParam(defaultValue = "-1.0") double timeBegin,
            @RequestParam(defaultValue = "-1.0") double timeEnd,
            @RequestParam String[] sources) {
        var sourceList = Arrays.stream(sources).map(Source::new).toList();

        String res;
        if (timeBegin < 0 && timeEnd < 0) {
            res = graphService.getGraphPointsBySource(sourceList);
        } else if (timeBegin < 0) {
            res = graphService.getGraphPointsBySourceAndPeriod(sourceList, 0., timeEnd);
        } else {
            res = graphService.getGraphPointsBySourceAndPeriod(sourceList, timeBegin, timeEnd);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(HTML.formatted(res));
    }
}
