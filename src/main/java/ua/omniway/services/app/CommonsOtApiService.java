package ua.omniway.services.app;

import com.google.common.base.Throwables;
import com.sun.xml.ws.client.ClientTransportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.omniway.InitializationException;
import ua.omniway.client.ot.soap.service.CommonApiService;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.CommonOtApi;

@Slf4j
@Service
public class CommonsOtApiService implements CommonOtApi {
    public static final String DISPATCHER_SCRIPT = "Api.Commons.Dispatcher";
    private final CommonApiService commonApiService;

    public CommonsOtApiService(CommonApiService commonApiService) {
        this.commonApiService = commonApiService;
    }

    /**
     * Check connection between bot and OMNITRACKER
     *
     * @throws InitializationException if internal OMNITRACKER error
     */
    @Override
    public void checkConnection() {
        try {
            commonApiService.execute(DISPATCHER_SCRIPT, "CheckConnection", null);
        } catch (OmnitrackerException e) {
            throw new InitializationException("Error while executing checking connection script in OMNITRACKER", Throwables.getRootCause(e));
        }
    }

    @Override
    public void checkConnectionRecover(ClientTransportException e) {
        log.error("Error while checking connection to OMNITRACKER", Throwables.getRootCause(e));
        throw new InitializationException("Error while checking connection to OMNITRACKER", Throwables.getRootCause(e));
    }
}
