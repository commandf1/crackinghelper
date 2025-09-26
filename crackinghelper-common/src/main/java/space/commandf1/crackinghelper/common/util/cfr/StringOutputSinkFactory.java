package space.commandf1.crackinghelper.common.util.cfr;

import org.benf.cfr.reader.api.OutputSinkFactory;

import java.util.Collection;
import java.util.List;

/**
 * @author commandf1
 */
public class StringOutputSinkFactory implements OutputSinkFactory {
    private final StringBuilder output = new StringBuilder();

    @Override
    public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> availableSinks) {
        return List.of(SinkClass.STRING);
    }

    @Override
    public <T> Sink<T> getSink(SinkType sinkType, SinkClass sinkClass) {
        if (sinkType == SinkType.JAVA && sinkClass == SinkClass.STRING) {
            return sinkable -> {
                output.append((String) sinkable);
                output.append("\n");
            };
        }
        return ignore -> {};
    }

    public String getOutput() {
        return output.toString();
    }

    public void clearOutput() {
        output.setLength(0);
    }
}
