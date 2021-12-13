package nu.mine.mosher.io.slf4j;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import lombok.NonNull;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@WebListener
public class ServletLoggerInitializer implements ServletContextListener {
    private static Optional<ServletContext> ctx;

    static {
        setContext(Optional.empty());
    }

    @NonNull
    public synchronized static Optional<ServletContext> context() {
        return ctx;
    }

    private synchronized static void setContext(@NonNull final Optional<ServletContext> context) {
        ctx = context;
    }

    @Override
    public void contextInitialized(@NonNull final ServletContextEvent sce) {
        setContext(Optional.ofNullable(sce.getServletContext()));
    }

    @Override
    public void contextDestroyed(@NonNull final ServletContextEvent sce) {
        setContext(Optional.empty());
    }
}
