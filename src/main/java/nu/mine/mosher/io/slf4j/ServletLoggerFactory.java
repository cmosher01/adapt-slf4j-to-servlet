package nu.mine.mosher.io.slf4j;

import lombok.*;
import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;

import static org.slf4j.Logger.ROOT_LOGGER_NAME;

final class ServletLoggerFactory implements ILoggerFactory {
    @NonNull private final ConcurrentMap<String, Logger> loggers = new ConcurrentHashMap<>();

    @Override
    @NonNull
    public Logger getLogger(final String name) {
        return this.loggers.computeIfAbsent(clean(name), ServletLogger::new);
    }

    @NonNull
    private static String clean(Object anyObjectOrNull) {
        var name = "";
        if (Objects.nonNull(anyObjectOrNull)) {
            name = anyObjectOrNull.toString();
        }
        name = name.trim();
        if (name.isBlank()) {
            name = ROOT_LOGGER_NAME;
        }
        return name;
    }
}
