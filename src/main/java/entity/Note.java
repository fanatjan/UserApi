package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "note")
public class Note {

    @Max(50)
    private String title;

    @Max(1000)
    private String password;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    private User user;
}
