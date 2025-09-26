package space.commandf1.crackinghelper.common.util;

import java.util.List;

/**
 * @author commandf1
 */
public class ListUtil {
    public static String linesToString(List<String> list) {
        if (list == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
