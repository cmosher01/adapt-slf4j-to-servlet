package nu.mine.mosher.io.slf4j;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import lombok.NonNull;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@WebListener
public class ServletLoggerContext implements ServletContextListener {
    private static Optional<ServletContext> context;

    static {
        set(Optional.empty());
    }

    private synchronized static void set(@NonNull final Optional<ServletContext> context) {
        ServletLoggerContext.context = context;
    }

    @NonNull
    public synchronized static Optional<ServletContext> get() {
        return ServletLoggerContext.context;
    }

    @Override
    public void contextInitialized(@NonNull final ServletContextEvent event) {
        set(Optional.ofNullable(event.getServletContext()));
    }

    @Override
    public void contextDestroyed(@NonNull final ServletContextEvent event) {
        set(Optional.empty());
    }
}
