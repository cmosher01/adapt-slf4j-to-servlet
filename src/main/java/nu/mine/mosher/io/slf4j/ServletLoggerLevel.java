package nu.mine.mosher.io.slf4j;

import lombok.NonNull;
import org.slf4j.event.Level;

import java.util.concurrent.atomic.AtomicInteger;

final class ServletLoggerLevel {
    @NonNull private final AtomicInteger level = new AtomicInteger(Level.TRACE.toInt());

    public void set(@NonNull final Level level) {
        this.level.set(level.toInt());
    }

    @NonNull
    public Level get() {
        return Level.intToLevel(this.level.get());
    }

    public boolean is(@NonNull final Level level) {
        // 0 = TRACE < DEBUG < INFO < WARN < ERROR
        return this.level.get() <= level.toInt();
    }
}
