package mybook.cache;

import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class MemcachedService {
    private static final Logger logger = LoggerFactory.getLogger(MemcachedService.class);

    private static final String memcached = "memcached";
    private static final String memcachedPort = "memcachedPort";
    private static MemcachedService cacheService = null;

    private MemcachedClient memcachedClient;
    private final int expirationSeconds = 300;

    private MemcachedService() {
        init();
    }

    public void init() {
        try {
            // System.getProperty() not valid
            String endpoint = System.getenv(memcached);
            String port = System.getenv(memcachedPort);
            logger.info("Memcached endpoint: {}, port: {}", endpoint, port);
            memcachedClient = new MemcachedClient(new InetSocketAddress(endpoint, Integer.parseInt(port)));
            logger.info("memcached client was created");
        } catch (Exception e) {
            logger.error("Failed to initialize memcached service", e);
        }
    }

    public static MemcachedService getInstance() {
        if (cacheService == null) {
            cacheService = new MemcachedService();
        }
        return cacheService;
    }

    public String get(String key) {
        if (memcachedClient == null) {
            return null;
        }
        Object cached = memcachedClient.get(key);
        return cached == null? null : cached.toString();
    }

    public void set(String key, String value) {
        if (memcachedClient == null) {
            return;
        }
        memcachedClient.set(key, expirationSeconds, value);
    }
}
