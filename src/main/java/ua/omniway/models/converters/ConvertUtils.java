package ua.omniway.models.converters;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;

public class ConvertUtils {

    private ConvertUtils() {
    }

    public static LocalDateTime convertToLocalDateTime(XMLGregorianCalendar xgc) {
        return xgc.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }

    private static LocalDateTime convertToLocalDateTimeWithTimezone(XMLGregorianCalendar xgc) {
        final int offsetSeconds = xgc.toGregorianCalendar().toZonedDateTime().getOffset().getTotalSeconds();
        final LocalDateTime localDateTime = xgc.toGregorianCalendar().toZonedDateTime().toLocalDateTime(); // this simply ignores the timeZone
        return localDateTime.minusSeconds(offsetSeconds); // adjust according to the time-zone offset
    }

    public static XMLGregorianCalendar convertToXMLGregorianCalendar(LocalDateTime dateTime) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime.toString());
    }
}
