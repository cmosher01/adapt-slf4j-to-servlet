package nu.mine.mosher.io.slf4j;

import lombok.*;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.LegacyAbstractLogger;

import java.util.*;

final class ServletLogger extends LegacyAbstractLogger {
    public ServletLogger(@NonNull final String name) {
        this.name = name;
    }



    @NonNull private final ServletLoggerLevel level = new ServletLoggerLevel();

    @SuppressWarnings("unused")
    public void setLevel(@NonNull final Level level) {
        this.level.set(level);
    }

    @SuppressWarnings("unused")
    @NonNull
    public Level getLevel() {
        return this.level.get();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.level.is(Level.TRACE);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.level.is(Level.DEBUG);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.level.is(Level.INFO);
    }

    @Override
    public boolean isWarnEnabled() {
        return this.level.is(Level.WARN);
    }

    @Override
    public boolean isErrorEnabled() {
        return this.level.is(Level.ERROR);
    }





    @Override
    protected void handleNormalizedLoggingCall(final Level level, final Marker marker, final String msg, final Object[] args, final Throwable throwable) {
        val line = ServletLoggerFormatter.buildLogLine(level, marker, getName(), msg, args);
        val printer = new ServletLoggerPrinter(line, Optional.ofNullable(throwable));
        printer.write();
    }





    @NonNull private static final String SELF = ServletLogger.class.getName();

    @Override
    @NonNull
    protected String getFullyQualifiedCallerName() {
        return ServletLogger.SELF;
    }
}
