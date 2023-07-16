package service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mpei.latushkina.fqw.FqwApplicationWOServer;
import ru.mpei.latushkina.fqw.service.ComtradeUploadService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FqwApplicationWOServer.class)
public class TestComtradeUploadService {
    @Autowired
    private ComtradeUploadService comtradeUploadService;


    @Test
    public void testYourService() {
        comtradeUploadService.upload(null, null);
    }
}
