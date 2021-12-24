package ua.omniway.dao.ot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.omniway.client.ot.soap.Filter;
import ua.omniway.client.ot.soap.ObjectData;
import ua.omniway.client.ot.soap.ObjectFactory;
import ua.omniway.client.ot.soap.RequiredField;
import ua.omniway.client.ot.soap.operations.SoapOperations;
import ua.omniway.client.ot.soap.service.SimpleOtCrud;
import ua.omniway.client.ot.soap.types.LongIntVal;
import ua.omniway.dao.exceptions.DaoException;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.models.ot.Incident;
import ua.omniway.models.ot.ProcessData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class IncidentsDao extends SimpleOtCrud<Incident> {
    public static final String SR_CALLER_FILTER = "botGetIncByReportingPerson";
    private final ProcessDataDao processDataDao;

    @Autowired
    public IncidentsDao(SoapOperations soapOperations, ProcessDataDao processDataDao) {
        super(soapOperations);
        this.processDataDao = processDataDao;
    }

    @Override
    protected List<RequiredField> getRequiredFields() {
        return processDataDao.getRequiredFields();
    }

    /**
     * @return {@link Filter} definition to get incidents for defined caller
     */
    private Filter getCallerFilter(long value) {
        Filter filter = new Filter();
        filter.setName(SR_CALLER_FILTER);
        filter.getContent().add(new ObjectFactory().createFilterLongIntVal(new LongIntVal("ReportingPerson.ID", value)));
        return filter;
    }

    /**
     * Call same method of "parent" DAO and then cast result to {@link Incident}
     * because all field are same
     */
    @Override
    protected List<Incident> parseFind(List<ObjectData> objects) {
        final List<ProcessData> processData = processDataDao.parseFind(objects);
        return processData.stream().map(Incident::new).collect(Collectors.toList());
    }

    /**
     * Get {@link Incident} list from OMNITRACKER for specified caller unique id
     *
     * @param callerId caller unique id in OMNITRACKER
     * @return {@link Incident} list. May be empty
     * @throws DaoException Exception while executing soap request
     */
    public List<Incident> getRequestsForCaller(long callerId) throws DaoException {
        List<Incident> list = new ArrayList<>();
        log.debug("Getting all incidents for caller otUniqueId {}", callerId);
        try {
            list = findAll(Incident.FOLDER.getAbsolutPath(), getCallerFilter(callerId));
        } catch (OmnitrackerException e) {
            throw new DaoException(e);
        }
        return list;
    }
}
