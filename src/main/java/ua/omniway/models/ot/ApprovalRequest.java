package ua.omniway.models.ot;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.omniway.client.ot.soap.types.OtFolder;
import ua.omniway.models.converters.ConvertUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;

import static ua.omniway.models.ot.FoldersEnum.APPROVAL_REQUEST;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class ApprovalRequest extends ProcessData {
    public static final OtFolder FOLDER = APPROVAL_REQUEST.getFolder();
    public static final String COMMENT_ALIAS = "Comment";
    public static final String DATE_APPROVED_ALIAS = "DateApproved";
    public static final String APPROVAL_RESULT_ALIAS = "ApprovalResult";

    private String comment;
    private LocalDateTime dateApproved;
    private ApprovalResultEnum approvalResult;

    public ApprovalRequest() {
    }

    public ApprovalRequest(long uniqueId) {
        super(uniqueId);
    }

    public ApprovalRequest(ProcessData other) {
        super(other);
    }

    public ApprovalRequest setDateApproved(LocalDateTime dateApproved) {
        this.dateApproved = dateApproved;
        return this;
    }

    public ApprovalRequest setDateApproved(XMLGregorianCalendar xgc) {
        this.dateApproved = ConvertUtils.convertToLocalDateTime(xgc);
        return this;
    }
}
