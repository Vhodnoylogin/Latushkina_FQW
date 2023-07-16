package util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.mpei.latushkina.fqw.util.DataPointReader;

@Slf4j
public class DataPointReaderTest {
    //    private static String filePath = "test/source/src.csv";
    private static final String filePath = "E:\\IdeaProjects\\FQW\\src\\main\\resources\\test\\source\\src.csv";

    @Test
    public void testReadFileAndPrint() {
        var res = DataPointReader.readDataPointsFromFile(filePath);

        log.info("{}", res);
    }
}
