package nu.mine.mosher.io.slf4j;

import lombok.*;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.*;

import static java.time.temporal.ChronoField.*;

final class ServletLoggerFormatter {
    private ServletLoggerFormatter() {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public static String buildLogLine(final Level level, final Marker marker, @NonNull final String name, final String msg, final Object[] args) {
        val sTimeStamp = buildTimeStamp();
        val sLevel = buildLevel(level);
        val sLogger = buildLogger(name);
        val sMarker = buildMarker(marker);
        val sMessage = MessageFormatter.basicArrayFormat(msg, args);

        return
            Stream.of(sTimeStamp, sLevel, sLogger, sMarker, sMessage)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(" "));
    }


    @NonNull private static final DateTimeFormatter FORMATTER_TIMESTAMP = formatterTimestamp();

    @NonNull
    private static DateTimeFormatter formatterTimestamp() {
        return
            new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .appendLiteral('T')
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2)
                .appendFraction(NANO_OF_SECOND, 9, 9, true)
                .appendOffsetId()
            .toFormatter();
    }

    @NonNull
    private static String buildTimeStamp() {
        return FORMATTER_TIMESTAMP.format(ZonedDateTime.now(ZoneOffset.UTC));
    }

    @NonNull
    private static String buildLevel(final Level level) {
        return "["+ Optional.ofNullable(level).orElse(Level.ERROR).toString().trim()+"]";
    }

    @NonNull
    private static String buildLogger(@NonNull final String name) {
        return name;
    }

    @NonNull
    private static String buildMarker(final Marker marker) {
        if (Objects.isNull(marker)) {
            return "";
        }
        return Optional.ofNullable(marker.getName()).orElse("").trim();
    }
}
