package ua.omniway.models.converters;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.omniway.dao.ot.AttachmentMeta;
import ua.omniway.models.db.AttachmentInfo;

@Mapper
public interface AttachmentInfoAttachmentMetaMapper {
    AttachmentInfoAttachmentMetaMapper INSTANCE = Mappers.getMapper(AttachmentInfoAttachmentMetaMapper.class);

    AttachmentMeta toAttachmentMeta(AttachmentInfo info);
}
