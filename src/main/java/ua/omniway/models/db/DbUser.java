package ua.omniway.models.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.omniway.models.converters.DbUserActiveActionConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "users")
public class DbUser {
    @Id
    @Column(name = "userId")
    private Long userId;

    @Column(name = "chatId", nullable = false)
    private long chatId;

    @Column(name = "subscriberUniqueId", nullable = false)
    private long subscriberId;

    @Column(name = "personOtUniqueId", nullable = false)
    private long personId;

    @NotNull(message = "DbUser.ActiveAction can not be null")
    @Column(name = "activeAction")
    @Convert(converter = DbUserActiveActionConverter.class)
    private ActiveAction activeAction;

    @NotNull(message = "DbUser.UserStatus can not be null")
    @Column(name = "isActive")
    @Enumerated(value = EnumType.ORDINAL)
    private UserStatus isActive;

    @ToString.Exclude
    @OneToOne(mappedBy = "dbUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserSetting setting;

    @ToString.Exclude
    @OneToOne(mappedBy = "dbUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ApprovalVoteTemp approvalVoteTemp;

    @ToString.Exclude
    @OneToOne(mappedBy = "dbUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ServiceCallFeedbackDraft serviceCallFeedbackDraft;

    @ToString.Exclude
    @OneToOne(mappedBy = "dbUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private InteractionsDraft interactionsDraft;

    @ToString.Exclude
    @OneToOne(mappedBy = "dbUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ServiceCallDraft serviceCallDraft;

    public DbUser() {
    }

    public DbUser(Long userId, long chatId, ActiveAction activeAction, UserStatus isActive, UserSetting setting) {
        this.userId = userId;
        this.chatId = chatId;
        this.activeAction = activeAction;
        this.isActive = isActive;
        this.setting = setting;
    }

    public boolean isActive() {
        return this.isActive.ordinal() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbUser dbUser = (DbUser) o;
        return userId.equals(dbUser.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
