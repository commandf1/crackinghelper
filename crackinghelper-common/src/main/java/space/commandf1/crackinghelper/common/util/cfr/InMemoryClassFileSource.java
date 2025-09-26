package space.commandf1.crackinghelper.common.util.cfr;

import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;

import java.util.*;

/**
 * @author commandf1
 */
public class InMemoryClassFileSource implements ClassFileSource {
    private final Map<String, byte[]> classBytesMap;
    private final Map<String, String> classPathToInternalName;

    public InMemoryClassFileSource(Map<String, byte[]> classBytesMap) {
        this.classBytesMap = new HashMap<>(classBytesMap);
        this.classPathToInternalName = new HashMap<>();
        for (String key : classBytesMap.keySet()) {
            classPathToInternalName.put(key + ".class", key);
        }
    }

    @Override
    public void informAnalysisRelativePathDetail(String usePath, String classFilePath) {}

    @Override
    public Collection<String> addJar(String jarPath) {
        return List.of();
    }

    @Override
    public String getPossiblyRenamedPath(String path) {
        return path;
    }

    @Override
    public Pair<byte[], String> getClassFileContent(String classFilePath) throws IllegalStateException {
        String internalName = classPathToInternalName.get(classFilePath);
        if (internalName != null) {
            byte[] bytes = classBytesMap.get(internalName);
            if (bytes != null) {
                return Pair.make(bytes, null);
            }
        }
        return null;
    }
}
