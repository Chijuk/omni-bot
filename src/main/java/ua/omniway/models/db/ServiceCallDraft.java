package ua.omniway.models.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "service_calls_drafts")
public class ServiceCallDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_calls_drafts_generator")
    @SequenceGenerator(name = "service_calls_drafts_generator", sequenceName = "shared_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "ServiceCallDraft.userId can not be null")
    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "summary")
    private String summary;

    @Column(name = "summaryMessageId", unique = true)
    private Integer summaryMessageId;

    @NotNull(message = "ServiceCallDraft.wizardMessage can not be null")
    @Column(name = "wizardMessage", nullable = false, columnDefinition = "TEXT")
    @Convert(converter = MessageToJsonConverter.class)
    private Message wizardMessage;

    @Column(name = "sendMessage", columnDefinition = "TEXT")
    @Convert(converter = MessageToJsonConverter.class)
    private Message sendMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "FK_service_calls_drafts_userId"), updatable = false, insertable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private DbUser dbUser;

    @OneToMany(mappedBy = "serviceCallDraft", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @org.hibernate.annotations.ForeignKey(name = "none") // bug
    @ToString.Exclude
    private List<AttachmentInfo> attachments;

    public ServiceCallDraft() {
        // required by hibernate
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ServiceCallDraft draft = (ServiceCallDraft) o;
        return id != null && Objects.equals(id, draft.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}