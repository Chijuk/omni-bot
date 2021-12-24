package ua.omniway.client.ot.soap.service;

import ua.omniway.client.ot.soap.Filter;
import ua.omniway.client.ot.soap.RequiredField;
import ua.omniway.client.ot.soap.types.OtRequest;
import ua.omniway.dao.exceptions.OmnitrackerException;

import java.util.List;

public interface OtCrud<T extends OtRequest> {
    T findById(Long id, List<RequiredField> requiredFields) throws OmnitrackerException;

    T findById(Long id) throws OmnitrackerException;

    T findByFilter(String folderAlias, Filter filter) throws OmnitrackerException;

    List<T> findAll(List<Long> idList) throws OmnitrackerException;

    List<T> findAll(String folderAlias, Filter filter) throws OmnitrackerException;

    List<T> findAll(String folderAlias, Filter filter, List<RequiredField> requiredFields, boolean recursive) throws OmnitrackerException;

    void modify(Long uniqueId, List<Object> values) throws OmnitrackerException;
}
