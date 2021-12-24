package ua.omniway.dao.ot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.omniway.client.ot.soap.ObjectData;
import ua.omniway.client.ot.soap.RequiredField;
import ua.omniway.client.ot.soap.operations.SoapOperations;
import ua.omniway.client.ot.soap.service.CommonApiService;
import ua.omniway.client.ot.soap.service.SimpleOtCrud;
import ua.omniway.client.ot.soap.types.DateTimeVal;
import ua.omniway.client.ot.soap.types.StringVal;
import ua.omniway.models.ot.ApprovalRequest;
import ua.omniway.models.ot.ApprovalResultEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ua.omniway.models.ot.ApprovalRequest.*;

@Slf4j
@Component
public class ApprovalRequestsDao extends SimpleOtCrud<ApprovalRequest>{
    private final ProcessDataDao processDataDao;

    @Autowired
    protected ApprovalRequestsDao(SoapOperations soapOperations, ProcessDataDao processDataDao, CommonApiService commonApiService) {
        super(soapOperations);
        this.processDataDao = processDataDao;
    }

    @Override
    protected List<ApprovalRequest> parseFind(List<ObjectData> objects) {
        if (objects != null && !objects.isEmpty()) {
            Map<Long, ApprovalRequest> childResultMap = new HashMap<>();
            for (ObjectData objectData : objects) {
                ApprovalRequest approvalRequest = new ApprovalRequest();
                approvalRequest.setUniqueId(objectData.getId());
                for (Object otValue : objectData.getOtValues()) {
                    if (otValue instanceof StringVal && APPROVAL_RESULT_ALIAS.equals(((StringVal) otValue).getName())) {
                        approvalRequest.setApprovalResult(ApprovalResultEnum.getByAlias(((StringVal) otValue).getValue()));
                    }
                    if (otValue instanceof StringVal && COMMENT_ALIAS.equals(((StringVal) otValue).getName())) {
                        approvalRequest.setComment(((StringVal) otValue).getValue());
                    }
                    if (otValue instanceof DateTimeVal && DATE_APPROVED_ALIAS.equals(((DateTimeVal) otValue).getName())) {
                        approvalRequest.setDateApproved(((DateTimeVal) otValue).getValue());
                    }
                }
                childResultMap.put(objectData.getId(), approvalRequest);
            }
            Map<Long, ApprovalRequest> parentResultMap = processDataDao.parseFind(objects).stream()
                    .map(ApprovalRequest::new).collect(Collectors.toMap(ApprovalRequest::getUniqueId, Function.identity()));
            childResultMap = mergeFindResults(parentResultMap, childResultMap);
            return new ArrayList<>(childResultMap.values());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @param parentResultMap parsed results map from parent object
     * @param childResultMap parsed results map from current object
     * @return merged {@link ApprovalRequest} map
     */
    private Map<Long, ApprovalRequest> mergeFindResults(Map<Long, ApprovalRequest> parentResultMap,
                                                        Map<Long, ApprovalRequest> childResultMap) {
        parentResultMap.keySet().forEach(aLong -> parentResultMap.merge(aLong, childResultMap.get(aLong),
                (approvalRequest, approvalRequest2) -> {
                    approvalRequest.setComment(approvalRequest2.getComment());
                    approvalRequest.setDateApproved(approvalRequest2.getDateApproved());
                    approvalRequest.setApprovalResult(approvalRequest2.getApprovalResult());
                    return approvalRequest;
                }));
        return parentResultMap;
    }

    /**
     * @return Combined required fields with ProcessData object
     */
    @Override
    protected List<RequiredField> getRequiredFields() {
        List<RequiredField> requiredFields = processDataDao.getRequiredFields();
        requiredFields.add(new RequiredField(COMMENT_ALIAS));
        requiredFields.add(new RequiredField(DATE_APPROVED_ALIAS));
        requiredFields.add(new RequiredField(DATE_CLOSED_ALIAS));
        requiredFields.add(new RequiredField(APPROVAL_RESULT_ALIAS));
        return requiredFields;
    }
}
