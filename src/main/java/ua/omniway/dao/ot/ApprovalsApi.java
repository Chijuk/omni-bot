package ua.omniway.dao.ot;

import ua.omniway.dao.exceptions.OmnitrackerException;

public interface ApprovalsApi {

    void setResultApproved(long objectUniqueId) throws OmnitrackerException;

    void setResultRejected(long objectUniqueId) throws OmnitrackerException;

    boolean canSetResult(long objectUniqueId, long userUniqueId) throws OmnitrackerException;
}
