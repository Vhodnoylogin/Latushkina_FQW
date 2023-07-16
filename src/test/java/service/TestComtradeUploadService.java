package service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mpei.latushkina.fqw.FqwApplicationWOServer;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.service.ComtradeUploadService;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FqwApplicationWOServer.class)
public class TestComtradeUploadService {
    @Autowired
    private ComtradeUploadService comtradeUploadService;

    @Test
    public void testYourService() {
        var res = comtradeUploadService.upload(null, null);

        log.info("{}", res.size());
        for (ChartPoint chartPoint : res) {
            log.info("{}", chartPoint.getPoints().size());
        }
        for (ChartPoint chartPoint : res) {
            log.info("{}", chartPoint.getPoints());
        }

    }
}
