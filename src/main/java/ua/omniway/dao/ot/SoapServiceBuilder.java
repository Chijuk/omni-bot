package ua.omniway.dao.ot;

import ua.omniway.client.ot.soap.service.OTWebServiceSoap;

import java.net.URL;

public interface SoapServiceBuilder {
    OTWebServiceSoap getService();

    OTWebServiceSoap getService(URL wsdlLocation);
}
