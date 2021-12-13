package nu.mine.mosher.io.slf4j;

import jakarta.servlet.ServletContext;
import lombok.*;

import java.util.Optional;

@RequiredArgsConstructor
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
final class ServletLoggerPrinter {
    @NonNull private final String message;
    @NonNull private final Optional<Throwable> throwable;


    public void write() {
        val ctx = ServletLoggerContext.get();
        if (ctx.isPresent()) {
            writeToServletContext(ctx.get());
        } else {
            writeToSysErr();
        }
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    private void writeToServletContext(@NonNull final ServletContext context) {
        synchronized (context) {
            if (this.throwable.isPresent()) {
                context.log(this.message, this.throwable.get());
            } else {
                context.log(this.message);
            }
        }
    }

    private void writeToSysErr() {
        System.err.println("Servlet context not found while logging: " + this.message);
        this.throwable.ifPresent(Throwable::printStackTrace);
        System.err.flush();
    }
}
