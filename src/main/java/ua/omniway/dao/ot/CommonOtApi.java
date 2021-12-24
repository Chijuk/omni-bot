package ua.omniway.dao.ot;

import com.sun.xml.ws.client.ClientTransportException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import ua.omniway.InitializationException;

import java.net.SocketTimeoutException;

public interface CommonOtApi {
    @Retryable(value = {SocketTimeoutException.class, ClientTransportException.class, InitializationException.class},
            maxAttempts = 60, backoff = @Backoff(delay = 60000))
    void checkConnection();

    @Recover
    void checkConnectionRecover(ClientTransportException e);
}
