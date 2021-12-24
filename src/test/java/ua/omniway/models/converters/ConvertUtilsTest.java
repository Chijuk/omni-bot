package ua.omniway.models.converters;

import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertUtilsTest {

    @Test
    void convertToLocalDateTime() throws DatatypeConfigurationException {
        XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-07-07T10:10:00");
        assertEquals(xgc.toString(), ConvertUtils.convertToLocalDateTime(xgc).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "Expect equal date strings");
    }

    @Test
    void convertToXMLGregorianCalendar() throws DatatypeConfigurationException {
        LocalDateTime ldt = LocalDateTime.parse("2020-11-21T12:09:33");
        assertEquals(ldt.toString(), ConvertUtils.convertToXMLGregorianCalendar(ldt).toString(), "Expect equal date strings");
    }
}