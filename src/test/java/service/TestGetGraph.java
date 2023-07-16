package service;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mpei.latushkina.fqw.FqwApplicationWOServer;
import ru.mpei.latushkina.fqw.model.dto.point.Source;
import ru.mpei.latushkina.fqw.service.ComtradeUploadService;
import ru.mpei.latushkina.fqw.service.graph.GraphService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FqwApplicationWOServer.class)
public class TestGetGraph {
    private final String filePath = "C:\\Users\\Radimir\\Downloads\\img.html";
    @Autowired
    private GraphService graphService;
    @Autowired
    private ComtradeUploadService comtradeUploadService;

    @Test
    @SneakyThrows
    public void testYourService() {
        comtradeUploadService.upload(null, null);
        var res = graphService.getGraphPointsBySource(List.of(
                new Source("src1"),
                new Source("src2")
        ));

//        log.info("Base64 = {}", res);
        var ph = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Пример вставки картинки Base64</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <img src=\"data:image/png;base64,%s\" alt=\"Картинка\">\n" +
                "</body>\n" +
                "</html>";
        var content = ph.formatted(res);


        var file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        Files.writeString(file.toPath(), content, StandardOpenOption.CREATE);
        System.out.println("Строка успешно сохранена в файл.");

    }
}
