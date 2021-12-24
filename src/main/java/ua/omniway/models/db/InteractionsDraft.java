package ua.omniway.models.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.omniway.models.MessagingDirection;
import ua.omniway.models.converters.InteractionDirectionConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "interactions_drafts")
public class InteractionsDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interaction_drafts_generator")
    @SequenceGenerator(name = "interaction_drafts_generator", sequenceName = "shared_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "InteractionsDraft.userId can not be null")
    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "text")
    private String text;

    @Column(name = "textMessageId", unique = true)
    private Integer textMessageId;

    @NotNull(message = "InteractionsDraft.parentUniqueId can not be null")
    @Column(name = "parentUniqueId", nullable = false)
    private Long parentUniqueId;

    @NotNull
    @Column(name = "direction", nullable = false)
    @Convert(converter = InteractionDirectionConverter.class)
    private MessagingDirection direction;

    @ToString.Exclude
    @Column(name = "sendMessage", columnDefinition = "TEXT")
    @Convert(converter = MessageToJsonConverter.class)
    private Message sendMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "FK_interactions_drafts_userId"), updatable = false, insertable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private DbUser dbUser;

    @OneToMany(mappedBy = "interactionDraft", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @org.hibernate.annotations.ForeignKey(name = "none") // bug
    @ToString.Exclude
    private List<AttachmentInfo> attachments;

    public InteractionsDraft() {
        // required by hibernate
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionsDraft that = (InteractionsDraft) o;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
