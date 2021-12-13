package nu.mine.mosher.io.slf4j;

import lombok.NonNull;
import org.slf4j.*;

import java.util.concurrent.*;

public final class ServletLoggerFactory implements ILoggerFactory {
    private final ConcurrentMap<String, Logger> loggers = new ConcurrentHashMap<>();

    ServletLoggerFactory() {
    }

    @Override
    @NonNull
    public Logger getLogger(@NonNull final String name) {
        return this.loggers.computeIfAbsent(name, k -> new ServletLogger(name));
    }
}
