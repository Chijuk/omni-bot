package ua.omniway.dao.ot;

import com.sun.xml.ws.developer.JAXWSProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ua.omniway.client.ot.soap.service.OTWebService;
import ua.omniway.client.ot.soap.service.OTWebServiceSoap;

import javax.xml.ws.BindingProvider;
import java.net.URL;

@Slf4j
@Component
public class DefaultSoapServiceBuilder implements SoapServiceBuilder {
    static {
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.FINER); //FINER -> DEBUG
    }

    private static final String DEFAULT_WSDL_PATH = "/wsdl/ot.wsdl";
    @Value("${ot.url}")
    private String otUrl;
    @Value("${ot.user}")
    private String otUser;
    @Value("${ot.password}")
    private String otPassword;

    private void configureService(OTWebServiceSoap service) {
        BindingProvider prov = (BindingProvider) service;
        if (!otUrl.isEmpty()) {
            prov.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, otUrl);
        }
        if (!otUser.isEmpty()) {
            prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, otUser);
        }
        if (!otPassword.isEmpty()) {
            prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, otPassword);
        }

        prov.getRequestContext().put(JAXWSProperties.CONNECT_TIMEOUT, 5000);
        prov.getRequestContext().put(JAXWSProperties.REQUEST_TIMEOUT, 30000);
    }

    @SneakyThrows
    public OTWebServiceSoap getService() {
        return this.getService(new ClassPathResource(DEFAULT_WSDL_PATH).getURL());
    }

    public OTWebServiceSoap getService(URL wsdlLocation) {
        OTWebService otWebService = new OTWebService(wsdlLocation);
        OTWebServiceSoap service = otWebService.getOTWebServiceSoap12();
        configureService(service);
        return service;
    }
}
