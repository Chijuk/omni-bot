package ua.omniway.models.ot;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.omniway.client.ot.soap.types.OtFolder;
import ua.omniway.client.ot.soap.types.OtRequest;
import ua.omniway.models.converters.ConvertUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;

import static ua.omniway.models.ot.FoldersEnum.PROCESS_DATA;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class ProcessData extends OtRequest {
    public static final OtFolder FOLDER = PROCESS_DATA.getFolder();
    public static final String ID_ALIAS = "Number";
    public static final String SUMMARY_ALIAS = "Summary";
    public static final String DESCRIPTION_ALIAS = "Description";
    public static final String DEADLINE_ALIAS = "DateDeadline";
    public static final String STATE_ALIAS = "State";
    public static final String OBJECT_TYPE_NAME_ALIAS = "ObjectType\\.ObjectTypeAlias";
    public static final String CALLER_ALIAS = "ReportingPerson";
    public static final String DATE_CLOSED_ALIAS = "DateClosed";

    private long id;
    private String summary;
    private String description = "";
    private LocalDateTime deadline;
    private String state;
    private Contact caller;
    private ObjectType objectType;
    private LocalDateTime dateClosed;

    public ProcessData() {
    }

    public ProcessData(long uniqueId) {
        super(uniqueId);
    }

    public ProcessData(ProcessData other) {
        super(other.getUniqueId());
        this.id = other.getId();
        this.summary = other.getSummary();
        this.deadline = other.getDeadline();
        this.state = other.getState();
        this.objectType = other.getObjectType();
        this.caller = other.getCaller();
        this.description = other.getDescription();
        this.dateClosed = other.getDateClosed();
    }

    public ProcessData setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    public ProcessData setDeadline(XMLGregorianCalendar xgc) {
        this.deadline = ConvertUtils.convertToLocalDateTime(xgc);
        return this;
    }

    public ProcessData setDateClosed(LocalDateTime dateClosed) {
        this.dateClosed = dateClosed;
        return this;
    }

    public ProcessData setDateClosed(XMLGregorianCalendar xgc) {
        this.dateClosed = ConvertUtils.convertToLocalDateTime(xgc);
        return this;
    }

}
