package nu.mine.mosher.io.slf4j;

import lombok.NonNull;
import org.slf4j.*;
import org.slf4j.helpers.*;
import org.slf4j.spi.*;

import java.util.Objects;

public final class ServletSlf4jServiceProvider implements SLF4JServiceProvider {
    private static String REQUESTED_API_VERSION;

    static {
        internalSetVersion("2.0.99");
    }

    private static synchronized void internalSetVersion(@SuppressWarnings("SameParameterValue") @NonNull final String version) {
        ServletSlf4jServiceProvider.REQUESTED_API_VERSION = version;
    }

    @NonNull
    private static synchronized String internalGetVersion() {
        return ServletSlf4jServiceProvider.REQUESTED_API_VERSION;
    }



    private ILoggerFactory loggerFactory;
    private IMarkerFactory markerFactory;
    private MDCAdapter mdcAdapter;

    @Override
    @NonNull
    public synchronized ILoggerFactory getLoggerFactory() {
        return Objects.requireNonNull(this.loggerFactory);
    }

    @Override
    @NonNull
    public synchronized IMarkerFactory getMarkerFactory() {
        return Objects.requireNonNull(this.markerFactory);
    }

    @Override
    @NonNull
    public synchronized MDCAdapter getMDCAdapter() {
        return Objects.requireNonNull(this.mdcAdapter);
    }

    @Override
    @NonNull
    public String getRequestedApiVersion() {
        return ServletSlf4jServiceProvider.internalGetVersion();
    }

    @Override
    public synchronized void initialize() {
        if (Objects.isNull(this.loggerFactory)) {
            this.loggerFactory = new ServletLoggerFactory();
            this.markerFactory = new BasicMarkerFactory();
            this.mdcAdapter = new NOPMDCAdapter();
        }
    }
}
