package ua.omniway.models.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "attachments_info")
public class AttachmentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachment_info_generator")
    @SequenceGenerator(name = "attachment_info_generator", sequenceName = "shared_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "AttachmentInfo.parentId can not be null")
    @Column(name = "parentId", nullable = false)
    private Long parentId;

    @NotNull(message = "AttachmentInfo.userId can not be null")
    @Column(name = "userId", nullable = false)
    private Long userId;

    @NotNull(message = "AttachmentInfo.replyMessageId can not be null")
    @Column(name = "replyMessageId", nullable = false, unique = true)
    private Integer replyMessageId;

    @Column(name = "OID")
    private String oid;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileSize")
    private Integer fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)) // bug
    @ToString.Exclude
    private ServiceCallDraft serviceCallDraft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)) // bug
    @ToString.Exclude
    private InteractionsDraft interactionDraft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)) // bug
    @ToString.Exclude
    private ServiceCallFeedbackDraft feedbackDraft;

    public AttachmentInfo() {
        // required by hibernate
    }

    public boolean isReady() {
        return !StringUtils.isEmpty(this.getOid()) && !StringUtils.isEmpty(this.getFileName()) && this.getFileSize() != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AttachmentInfo that = (AttachmentInfo) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
