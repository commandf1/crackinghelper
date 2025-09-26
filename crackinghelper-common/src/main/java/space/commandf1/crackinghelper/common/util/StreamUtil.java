package space.commandf1.crackinghelper.common.util;

import lombok.SneakyThrows;
import lombok.val;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author commandf1
 */
public class StreamUtil {
    @SneakyThrows
    public static String readAllLines(InputStream inputStream) {
        val stringBuilder = new StringBuilder();

        try (val reader = new BufferedReader(new InputStreamReader(inputStream))) {
             String line;
             while ((line = reader.readLine()) != null) {
                 stringBuilder.append(line).append("\n");
             }
        }

        return stringBuilder.toString();
    }
}
