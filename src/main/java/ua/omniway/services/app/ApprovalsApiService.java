package ua.omniway.services.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.omniway.client.ot.soap.service.CommonApiService;
import ua.omniway.client.ot.soap.types.LongIntVal;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.ApprovalRequestsDao;
import ua.omniway.dao.ot.ApprovalsApi;
import ua.omniway.models.ot.ApprovalRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApprovalsApiService implements ApprovalsApi {
    public static final String DISPATCHER_SCRIPT = "Api.Approvals.Dispatcher";
    public static final String OBJECT_ID = "objectId";
    public static final String USER_ID = "userId";

    private final CommonApiService commonApiService;
    private final ApprovalRequestsDao approvalRequestsDao;

    @Autowired
    public ApprovalsApiService(CommonApiService commonApiService, ApprovalRequestsDao approvalRequestsDao) {
        this.commonApiService = commonApiService;
        this.approvalRequestsDao = approvalRequestsDao;
    }

    /**
     * Set successful approval result for approval vote in OMNITRACKER.
     *
     * @param objectUniqueId approval vote unique id in OMNITRACKER
     * @throws OmnitrackerException Exception from OMNITRACKER
     */
    @Override
    public void setResultApproved(long objectUniqueId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        commonApiService.execute(DISPATCHER_SCRIPT, "SetResultApproved", params);
    }

    /**
     * Set not successful approval result for approval vote in OMNITRACKER.
     *
     * @param objectUniqueId approval vote unique id in OMNITRACKER
     * @throws OmnitrackerException Exception from OMNITRACKER
     */
    @Override
    public void setResultRejected(long objectUniqueId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        commonApiService.execute(DISPATCHER_SCRIPT, "SetResultRejected", params);
    }

    /**
     * Checks if user can set approval vote result now
     *
     * @param objectUniqueId approval vote unique id in OMNITRACKER
     * @param userUniqueId   subscriber unique id in OMNITRACKER
     * @return validation result
     * @throws OmnitrackerException Exception from OMNITRACKER
     */
    @Override
    public boolean canSetResult(long objectUniqueId, long userUniqueId) throws OmnitrackerException {
        List<Object> params = new ArrayList<>();
        params.add(new LongIntVal(OBJECT_ID, objectUniqueId));
        params.add(new LongIntVal(USER_ID, userUniqueId));
        return (boolean) commonApiService.execute(DISPATCHER_SCRIPT, "CanSetResult", params);
    }

    /**
     * @param objectUniqueId object unique id in OMNITRACKER
     * @return approval vote or null
     * @throws OmnitrackerException Exception from OMNITRACKER
     */
    public ApprovalRequest getApprovalRequest(long objectUniqueId) throws OmnitrackerException {
        return approvalRequestsDao.findById(objectUniqueId);
    }
}
