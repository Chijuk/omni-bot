package ua.omniway.models.db;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "approval_vote_temp")
public class ApprovalVoteTemp {
    @Id
    @Column(name = "userId")
    private Long userId;

    @Column(name = "otUniqueId", nullable = false)
    private long otUniqueId;

    @NotNull
    @ToString.Exclude
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    @Convert(converter = MessageToJsonConverter.class)
    private Message message;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "FK_approval_vote_temp_userId"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DbUser dbUser;

    public ApprovalVoteTemp() {
        // required by hibernate
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApprovalVoteTemp that = (ApprovalVoteTemp) o;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
