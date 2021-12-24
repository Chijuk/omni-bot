package ua.omniway.models.converters;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.omniway.models.db.ServiceCallCache;
import ua.omniway.models.ot.ProcessData;

@Mapper
public interface ProcessDataServiceCallCacheMapper {
    ProcessDataServiceCallCacheMapper INSTANCE = Mappers.getMapper(ProcessDataServiceCallCacheMapper.class);

    @ToProcessData
    ServiceCallCache processDataToServiceCallCache(ProcessData processData);
}
