package ua.omniway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.omniway.models.db.AttachmentInfo;

import java.util.List;

public interface AttachmentInfoRepository extends JpaRepository<AttachmentInfo, Long> {

    AttachmentInfo findByReplyMessageId(Integer messageId);

    List<AttachmentInfo> findByParentId(Long parentId);
}
