package ru.mpei.latushkina.fqw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mpei.latushkina.fqw.service.comtrade.ComtradeUploadService;

@Slf4j
@RestController
@RequestMapping("/api/v1/data/comtrade")
public class UploadComtradeController {
    private final ComtradeUploadService comtradeUploadService;

    @Autowired
    public UploadComtradeController(ComtradeUploadService comtradeUploadService) {
        this.comtradeUploadService = comtradeUploadService;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam MultipartFile cfg,
            @RequestParam MultipartFile dat) {
        log.debug("get two file {} {}",
                cfg.getOriginalFilename(),
                dat.getOriginalFilename()
        );

        try {
            comtradeUploadService.upload(cfg, dat);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Upload successfully end: cfg = " + cfg.getOriginalFilename() + " | dat = " + dat.getOriginalFilename());
        } catch (Exception e) {
            log.error("error", e);
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(e.getLocalizedMessage());
        }
    }
}
