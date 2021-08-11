package mybook.cache;

import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class MemcachedService {
    private static final Logger logger = LoggerFactory.getLogger(MemcachedService.class);

    private static final String memcached = "memcached";
    private static final String memcachedPort = "memcachedPort";

    private MemcachedClient memcachedClient;
    private static MemcachedService cacheService = null;
    private final int expirationSeconds = 120;

    private MemcachedService() {
        init();
    };

    public void init() {
        try {
            String endpoint = System.getenv(memcached);
            String portStr = System.getenv(memcachedPort);
            logger.info("Memcached endpoint: {}, port: {}", endpoint, portStr);
            int port = Integer.parseInt(portStr);
            memcachedClient = new MemcachedClient(new InetSocketAddress(endpoint, 11211));
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
            cacheService.init();
        }
        if (memcachedClient == null) {
            return null;
        }
        Object cached = memcachedClient.get(key);
        return cached == null? null : cached.toString();
    }

    public void set(String key, String value) {
        if (memcachedClient == null) {
            cacheService.init();
        }
        if (memcachedClient == null) {
            return;
        }
        memcachedClient.set(key, expirationSeconds, value);
    }
}
