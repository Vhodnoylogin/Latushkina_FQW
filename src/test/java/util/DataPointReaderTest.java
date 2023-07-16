package util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.mpei.latushkina.fqw.util.fortest.DataPointReader;

import java.io.File;

@Slf4j
public class DataPointReaderTest {
    private static final String filePath = "test/source/src.csv";


    @Test
    @SneakyThrows
    public void testReadFileAndPrint() {
        File file = DataPointReader.RESOURCE.getFile();
        log.info("{}", file);

        var res = DataPointReader.readDataPointsFromFile(file.getPath());

        log.info("{}", res);
    }
}
