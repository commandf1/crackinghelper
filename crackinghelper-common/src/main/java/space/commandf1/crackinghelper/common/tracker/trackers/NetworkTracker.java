package space.commandf1.crackinghelper.common.tracker.trackers;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.Nullable;
import space.commandf1.crackinghelper.common.convertor.plugin.CommonPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;
import space.commandf1.crackinghelper.common.tracker.ITracker;
import space.commandf1.crackinghelper.common.util.StreamUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.net.*;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


/**
 * @author commandf1
 */
public class NetworkTracker implements ITracker<Logger> {
    @Getter
    private static Logger logger;

    private final boolean detectResponse;

    public NetworkTracker(boolean detectResponse) {
        this.detectResponse = detectResponse;
    }

    @SneakyThrows
    @Override
    public void register(Logger logger) {
        NetworkTracker.logger = logger;
        ProxySelector.setDefault(new NetworkTrackerProxy(ProxySelector.getDefault()));

        if (this.detectResponse) {
            val factoryField = URL.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            val lookup = MethodHandles.lookup();
            val factory = (URLStreamHandlerFactory) lookup.unreflectGetter(factoryField).invoke();
            URL.setURLStreamHandlerFactory(new NetworkTrackerOfURLStreamHandlerFactory(factory));
        }

        logger.info("NetworkTracker registered.");
    }

    private static String getStringOfHeaderFields(Map<String, List<String>> headerFields) {
        StringBuilder sb = new StringBuilder("\n");
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            sb.append("  >> ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }

    private static class MonitoredURLConnection extends URLConnection {
        @Override
        public InputStream getInputStream() throws IOException {
            val inputStream = this.originalConnection.getInputStream();
            val responseOutput = StreamUtil.readAllLines(inputStream);
            String toPrint = "\n" + "=============== [ NETWORK MONITOR ] ===============" + "\n" +
                    " NETWORK CONNECTION RESPONSE DETECTED" + "\n" +
                    " URL: " + this.url.toString() + "\n" +
                    " Header Fields:" + NetworkTracker.getStringOfHeaderFields(this.originalConnection.getHeaderFields()) + "\n" +
                    " Content: " + this.originalConnection.getContent() + "\n" +
                    " Content Type: " + this.originalConnection.getContentType() + "\n" +
                    " Content Length: " + this.originalConnection.getContentLength() + "\n" +
                    " Allow User Interaction: " + this.originalConnection.getAllowUserInteraction() + "\n" +
                    " Use Cache: " + this.originalConnection.getUseCaches() + "\n" +
                    " Do Input: " + this.originalConnection.getDoInput() + "\n" +
                    " Do Output: " + this.originalConnection.getDoOutput() + "\n" +
                    " Default Use Cache: " + this.originalConnection.getDefaultUseCaches() + "\n" +
                    " Response Output: " + responseOutput + "\n" +
                    " TimeMillis: " + System.currentTimeMillis() + "\n" +
                    "================================================";
            logger.info(toPrint);

            return new ByteArrayInputStream(responseOutput.getBytes());
        }

        private final URLConnection originalConnection;

        public MonitoredURLConnection(URL url, URLConnection connection) {
            super(url);
            this.originalConnection = connection;
        }

        @Override
        public void connect() throws IOException {
            this.originalConnection.connect();
        }
    }

    private static class MonitoredHttpURLConnection extends HttpURLConnection {
        private final HttpURLConnection originalConnection;

        public MonitoredHttpURLConnection(URL url, HttpURLConnection connection) {
            super(url);

            this.originalConnection = connection;
        }

        @Override
        public void disconnect() {
            this.originalConnection.disconnect();
        }

        @Override
        public InputStream getInputStream() throws IOException {
            val inputStream = this.originalConnection.getInputStream();
            val responseOutput = StreamUtil.readAllLines(inputStream);
            String toPrint = "\n" + "=============== [ NETWORK MONITOR ] ===============" + "\n" +
                    " NETWORK CONNECTION RESPONSE DETECTED" + "\n" +
                    " URL: " + this.url.toString() + "\n" +
                    " Request Method: " + this.originalConnection.getRequestMethod() + "\n" +
                    " Response Code: " + this.originalConnection.getResponseCode() + "\n" +
                    " Response Message: " + this.originalConnection.getResponseMessage() + "\n" +
                    " Header Fields:" + NetworkTracker.getStringOfHeaderFields(this.originalConnection.getHeaderFields()) + "\n" +
                    " Content: " + this.originalConnection.getContent() + "\n" +
                    " Content Type: " + this.originalConnection.getContentType() + "\n" +
                    " Content Length: " + this.originalConnection.getContentLength() + "\n" +
                    " Allow User Interaction: " + this.originalConnection.getAllowUserInteraction() + "\n" +
                    " Use Cache: " + this.originalConnection.getUseCaches() + "\n" +
                    " Do Input: " + this.originalConnection.getDoInput() + "\n" +
                    " Do Output: " + this.originalConnection.getDoOutput() + "\n" +
                    " Default Use Cache: " + this.originalConnection.getDefaultUseCaches() + "\n" +
                    " Response Output: " + responseOutput + "\n" +
                    " TimeMillis: " + System.currentTimeMillis() + "\n" +
                    "================================================";
            logger.info(toPrint);

            return new ByteArrayInputStream(responseOutput.getBytes());
        }

        @Override
        public boolean usingProxy() {
            return this.originalConnection.usingProxy();
        }

        @Override
        public void connect() throws IOException {
            this.originalConnection.connect();
        }
    }

    private static class MonitoredHttpsURLConnection extends HttpsURLConnection {
        private final HttpsURLConnection originalConnection;

        public MonitoredHttpsURLConnection(URL url, HttpsURLConnection connection) {
            super(url);
            this.originalConnection = connection;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            val inputStream = this.originalConnection.getInputStream();
            val responseOutput = StreamUtil.readAllLines(inputStream);
            String toPrint = "\n" + "=============== [ NETWORK MONITOR ] ===============" + "\n" +
                    " NETWORK CONNECTION RESPONSE DETECTED" + "\n" +
                    " URL: " + this.url.toString() + "\n" +
                    " Request Method: " + this.originalConnection.getRequestMethod() + "\n" +
                    " Response Code: " + this.originalConnection.getResponseCode() + "\n" +
                    " Response Message: " + this.originalConnection.getResponseMessage() + "\n" +
                    " Header Fields:" + NetworkTracker.getStringOfHeaderFields(this.originalConnection.getHeaderFields()) + "\n" +
                    " Content: " + this.originalConnection.getContent() + "\n" +
                    " Content Type: " + this.originalConnection.getContentType() + "\n" +
                    " Content Length: " + this.originalConnection.getContentLength() + "\n" +
                    " Allow User Interaction: " + this.originalConnection.getAllowUserInteraction() + "\n" +
                    " Use Cache: " + this.originalConnection.getUseCaches() + "\n" +
                    " Do Input: " + this.originalConnection.getDoInput() + "\n" +
                    " Do Output: " + this.originalConnection.getDoOutput() + "\n" +
                    " Default Use Cache: " + this.originalConnection.getDefaultUseCaches() + "\n" +
                    " Response Output: " + responseOutput + "\n" +
                    " TimeMillis: " + System.currentTimeMillis() + "\n" +
                    "================================================";
            logger.info(toPrint);

            return new ByteArrayInputStream(responseOutput.getBytes());
        }

        @Override
        public String getCipherSuite() {
            return this.originalConnection.getCipherSuite();
        }

        @Override
        public Certificate[] getLocalCertificates() {
            return this.originalConnection.getLocalCertificates();
        }

        @Override
        public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
            return this.originalConnection.getServerCertificates();
        }

        @Override
        public void disconnect() {
            this.originalConnection.disconnect();
        }

        @Override
        public boolean usingProxy() {
            return this.originalConnection.usingProxy();
        }

        @Override
        public void connect() throws IOException {
            this.originalConnection.connect();
        }
    }


    private record NetworkTrackerOfURLStreamHandlerFactory(URLStreamHandlerFactory factory)
            implements URLStreamHandlerFactory {
        private static final Map<String, Method> ORIGINAL_OPEN_METHODS = new java.util.concurrent.ConcurrentHashMap<>();


        private Method getOpenConnectionMethod(String protocol) {
            return ORIGINAL_OPEN_METHODS.computeIfAbsent(protocol, p -> {
                try {
                    String className = "sun.net.www.protocol." + p + ".Handler";
                    Class<?> handlerClass = Class.forName(className);
                    val openMethod = handlerClass.getDeclaredMethod("openConnection", URL.class);
                    openMethod.setAccessible(true);
                    return openMethod;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }


        private static final Map<String, URLStreamHandler> DEFAULT_HANDLERS = new ConcurrentHashMap<>();


        private URLStreamHandler getDefaultHandler(String protocol) {
            return DEFAULT_HANDLERS.computeIfAbsent(protocol, p -> {
                try {
                    val className = "sun.net.www.protocol." + p + ".Handler";
                    val handlerClass = Class.forName(className);
                    val constructor = handlerClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    return (URLStreamHandler) constructor.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        @Override
        public URLStreamHandler createURLStreamHandler(String protocol) {
            if ("http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol)) {

                val originalHandler = getDefaultHandler(protocol);
                val openMethod = getOpenConnectionMethod(protocol);

                if (originalHandler == null || openMethod == null) {
                    if (factory != null) {
                        return factory.createURLStreamHandler(protocol);
                    }
                    return null;
                }

                return new URLStreamHandler() {
                    @Override
                    protected URLConnection openConnection(URL url) throws IOException {
                        try {
                            val originalConnection = (URLConnection) openMethod.invoke(originalHandler, url);

                            if (originalConnection instanceof HttpsURLConnection connection) {
                                return new MonitoredHttpsURLConnection(url, connection);
                            } else if (originalConnection instanceof HttpURLConnection connection) {
                                return new MonitoredHttpURLConnection(url, connection);
                            } else {
                                return new MonitoredURLConnection(url, originalConnection);
                            }

                        } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                            throw new IOException(e);
                        }
                    }
                };
            }


            if (factory != null) {
                return factory.createURLStreamHandler(protocol);
            }

            return null;
        }
    }

    private static class NetworkTrackerProxy extends ProxySelector {
        private final ProxySelector defaultSelector;

        public NetworkTrackerProxy(ProxySelector defaultSelector) {
            this.defaultSelector = defaultSelector;
        }

        private static final Map<ClassLoader, CommonPlugin<?>> PLUGIN_CACHE = new ConcurrentHashMap<>();

        private void collectPluginsCache() {
            val plugins = IPluginController.getController().getPlugins();
            if (plugins.size() != PLUGIN_CACHE.size() - 1) {
                PLUGIN_CACHE.clear();
                for (CommonPlugin<?> plugin : plugins) {
                    PLUGIN_CACHE.put(plugin.getPluginClassLoader(), plugin);
                }
                PLUGIN_CACHE.remove(this.getClass().getClassLoader());
            }
        }

        private StackTraceElement getOperatingStackTraceElement() {
            val stackTrace = new Exception().getStackTrace();
            this.collectPluginsCache();
            for (StackTraceElement element : stackTrace) {
                try {
                    val loader = Class.forName(element.getClassName(), false, this.getClass().getClassLoader()).getClassLoader();
                    if (loader == null) {
                        continue;
                    }
                    if (PLUGIN_CACHE.containsKey(loader)) {
                        return element;
                    }
                } catch (ClassNotFoundException ignored) {
                }
            }

            return null;
        }

        private CommonPlugin<?> getOperatingPlugin(@Nullable StackTraceElement element) {
            if (element == null) {
                return null;
            }

            try {
                val loader = Class.forName(element.getClassName(), false, NetworkTrackerProxy.class.getClassLoader()).getClassLoader();
                return PLUGIN_CACHE.get(loader);
            } catch (ClassNotFoundException ignored) {
            }

            return null;
        }

        @SneakyThrows
        @Override
        public List<Proxy> select(URI uri) {
            val operatingStackTraceElement = this.getOperatingStackTraceElement();
            val plugin = this.getOperatingPlugin(operatingStackTraceElement);

            if (plugin == null) {
                return this.defaultSelector.select(uri);
            }

            val formattedStackTraceMessage = String.format("%s#%s(%s:%s)",
                    operatingStackTraceElement.getClassName(),
                    operatingStackTraceElement.getMethodName(),
                    operatingStackTraceElement.getFileName(),
                    operatingStackTraceElement.getLineNumber()
            );

            val urlString = uri.toString();
            val pluginName = plugin.getName();
            val operatingStackTraceElementString = operatingStackTraceElement.toString();

            String toPrint = "\n" + "=============== [ NETWORK MONITOR ] ===============" + "\n" +
                    " NETWORK CONNECTION DETECTED" + "\n" +
                    " URL: " + urlString + "\n" +
                    " Plugin: " + pluginName + "\n" +
                    " StackTrace(raw): " + operatingStackTraceElementString + "\n" +
                    " StackTrace(formatted): " + formattedStackTraceMessage + "\n" +
                    " TimeMillis: " + System.currentTimeMillis() + "\n" +
                    "================================================";

            logger.info(toPrint);

            return this.defaultSelector.select(uri);
        }

        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            this.defaultSelector.connectFailed(uri, sa, ioe);
        }
    }
}