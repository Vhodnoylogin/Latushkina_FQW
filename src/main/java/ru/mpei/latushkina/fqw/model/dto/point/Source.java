package ru.mpei.latushkina.fqw.model.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Source {
    private final static String DELIMITER = "_";
    private String name;
    private Integer channelId;

    public static Source makeSource(String raw) {
        var split = raw.split(DELIMITER);
        var num = Integer.parseInt(split[0]);
        var name = split[1];

        return new Source(num, name);
    }

    public String getDescription() {
        return channelId.toString() + DELIMITER + name;
    }
}
