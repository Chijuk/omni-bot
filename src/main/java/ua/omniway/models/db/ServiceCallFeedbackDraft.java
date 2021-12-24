package ua.omniway.models.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name = "service_call_feedback_drafts")
public class ServiceCallFeedbackDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_call_feedback_drafts_generator")
    @SequenceGenerator(name = "service_call_feedback_drafts_generator", sequenceName = "shared_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "ServiceCallFeedbackDraft.userId can not be null")
    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "text")
    private String text;

    @Column(name = "textMessageId", unique = true)
    private Integer textMessageId;

    @NotNull(message = "ServiceCallFeedbackDraft.parentUniqueId can not be null")
    @Column(name = "parentUniqueId", nullable = false)
    private Long parentUniqueId;

    @NotNull(message = "ServiceCallFeedbackDraft.wizardMessage can not be null")
    @Column(name = "wizardMessage", nullable = false, columnDefinition = "TEXT")
    @Convert(converter = MessageToJsonConverter.class)
    private Message wizardMessage;

    @ToString.Exclude
    @Column(name = "sendMessage", columnDefinition = "TEXT")
    @Convert(converter = MessageToJsonConverter.class)
    private Message sendMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "FK_service_call_feedback_drafts_userId"), updatable = false, insertable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private DbUser dbUser;

    @OneToMany(mappedBy = "feedbackDraft", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @org.hibernate.annotations.ForeignKey(name = "none") // bug
    @ToString.Exclude
    private List<AttachmentInfo> attachments;

    public ServiceCallFeedbackDraft() {
        // required by hibernate
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ServiceCallFeedbackDraft that = (ServiceCallFeedbackDraft) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
