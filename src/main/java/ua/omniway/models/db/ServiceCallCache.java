package ua.omniway.models.db;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import ua.omniway.models.ot.ObjectType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "service_calls_cache")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ServiceCallCache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "otUniqueId", nullable = false)
    private long otUniqueId;

    @Column(name = "scId", nullable = false)
    private long scId;

    @Column(name = "state", nullable = false)
    private String state;

    @NotNull
    @Column(name = "category", nullable = false)
    @Convert(converter = ObjectTypeConverter.class)
    private ObjectType category;

    @NotNull
    @NotEmpty
    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(name = "information", columnDefinition = "TEXT")
    private String information;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "caller", nullable = false)
    private long caller;

    @NotNull
    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "messageId")
    @ColumnDefault("0")
    private long messageId = 0;

    public ServiceCallCache() {
        // required by hibernate
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceCallCache that = (ServiceCallCache) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
