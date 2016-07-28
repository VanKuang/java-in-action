package cn.van.kuang.java.core.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Provide configuration base on properties file
 * Will schedule scan file to update configurations
 */
public class ConfigurationProvider {

    private final static Logger logger = LoggerFactory.getLogger(ConfigurationProvider.class);

    private static final long DEFAULT_SCAN_INTERVAL = 10 * 1000L;

    private final String path;

    private final long scanInterval;

    private final ScheduledExecutorService scannerService = Executors.newSingleThreadScheduledExecutor();

    private Properties properties;

    private Lock lock = new ReentrantLock();

    public ConfigurationProvider(String path) {
        this(path, DEFAULT_SCAN_INTERVAL);
    }

    public ConfigurationProvider(String path, long scanInterval) {
        this.path = path;
        this.scanInterval = scanInterval;

        try {
            loadFile();
        } catch (Exception e) {
            logger.error("Fail to load [" + path + "]", e);
            throw new RuntimeException(e);
        }

        startScanner();
    }

    public String get(String key) {
        try {
            lock.lock();
            return properties.getProperty(key);
        } finally {
            lock.unlock();
        }
    }

    public void dispose() {
        scannerService.shutdown();
    }

    private void startScanner() {
        if (scanInterval <= 0) {
            logger.info("Won't start scanner, due to scanInterval=[{}]", scanInterval);
        }

        scannerService.scheduleWithFixedDelay(
                (Runnable) () -> {
                    try {
                        loadFile();
                    } catch (Exception e) {
                        logger.error("Fail to load " + path, e);
                    }
                },
                1000,
                scanInterval,
                TimeUnit.MILLISECONDS
        );
    }

    private void loadFile() throws Exception {
        Properties tmp = new Properties();
        FileInputStream inputStream = new FileInputStream(path);
        tmp.load(inputStream);

        try {
            lock.lock();
            properties = tmp;

            logger.info("Reload [{}]", path);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConfigurationProvider provider = new ConfigurationProvider(
                "/Users/VanKuang/Development/workspace/java-in-action/java-core/src/main/resources/configuration.properties",
                1000);

        while (Thread.currentThread().isAlive()) {
            if (Thread.currentThread().isInterrupted()) {
                provider.dispose();
                break;
            }

            System.out.println(provider.get("key1"));
            System.out.println(provider.get("key2"));

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignored) {
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(provider::dispose));
    }
}
