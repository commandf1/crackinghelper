package space.commandf1.crackinghelper.common.tracker;

/**
 * @author commandf1
 */
public class TrackerManager {
    private static TrackerManager manager;

    public static TrackerManager getManager() {
        return manager == null ? manager = new TrackerManager() : manager;
    }

    public final <T> void register(ITracker<T> tracker, T value) {
        tracker.register(value);
    }
}
