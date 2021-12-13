package nu.mine.mosher.io.slf4j;

import lombok.*;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.*;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.*;

public final class ServletLogger extends LegacyAbstractLogger {
    private final AtomicBoolean verbose = new AtomicBoolean(true);

    ServletLogger(@NonNull final String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public void setVerbose(final boolean verbose) {
        this.verbose.set(verbose);
    }

    public boolean getVerbose() {
        return this.verbose.get();
    }

    @Override
    public boolean isTraceEnabled() {
        return getVerbose();
    }

    @Override
    public boolean isDebugEnabled() {
        return getVerbose();
    }

    @Override
    public boolean isInfoEnabled() {
        return getVerbose();
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @SuppressWarnings("FieldMayBeFinal")
    private static String SELF = ServletLogger.class.getName();

    @Override
    @NonNull
    protected String getFullyQualifiedCallerName() {
        return SELF;
    }

    @Override
    protected void handleNormalizedLoggingCall(final Level level, final Marker marker, final String msg, final Object[] args, final Throwable throwable) {
        val sTimeStamp = buildTimeStamp();
        val sLevel = buildLevel(level);
        val sLogger = buildLogger();
        val sMarker = buildMarker(marker);
        val sMessage = MessageFormatter.basicArrayFormat(msg, args);
        val sStackTrace = buildStackTrace(throwable);

        val m =
            Stream.of(sTimeStamp, sLevel, sLogger, sMarker, sMessage, sStackTrace)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(" "));

        val ctx = ServletLoggerInitializer.context();
        if (ctx.isPresent()) {
            synchronized (ctx.get()) {
                ctx.get().log(m);
            }
        } else {
            System.err.println("Servlet context not found while logging: " + m);
            System.err.flush();
        }
    }

    @NonNull
    private static String buildTimeStamp() {
        val now = ZonedDateTime.now(ZoneId.of("Z"));
        return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(now);
    }

    @NonNull
    private static String buildLevel(final Level level) {
        return "["+Optional.ofNullable(level).orElse(Level.ERROR).toString().trim()+"]";
    }

    @NonNull
    private String buildLogger() {
        return getName().trim();
    }

    @NonNull
    private static String buildMarker(final Marker marker) {
        if (Objects.isNull(marker)) {
            return "";
        }
        return marker.getName().trim();
    }

    @NonNull
    private static String buildStackTrace(final Throwable throwable) {
        if (Objects.isNull(throwable)) {
            return "";
        }
        val s = new StringWriter(1024);
        val w = new PrintWriter(s, true);
        w.println();
        w.println("================");
        throwable.printStackTrace(w);
        w.print("================");
        return s.toString();
    }
}
