package ua.omniway.models.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.omniway.services.app.L10n;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_settings")
public class UserSetting {
    @Id
    @Column(name = "userId")
    private Long userId;

    @NotNull
    @NotEmpty
    @Column(name = "language", nullable = false, length = 10)
    @ColumnDefault("'" + L10n.DEFAULT_LANGUAGE_CODE + "'")
    private String language;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "FK_user_settings_userId"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private DbUser dbUser;

    public UserSetting() {
    }

    public UserSetting(String language) {
        this.language = language;
    }

    public UserSetting(Long userId, String language) {
        this.userId = userId;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSetting that = (UserSetting) o;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
