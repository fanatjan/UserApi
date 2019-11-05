package entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "user")
public class User {

    private String email;

    private String password;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Note> notes = new HashSet<>();
}
