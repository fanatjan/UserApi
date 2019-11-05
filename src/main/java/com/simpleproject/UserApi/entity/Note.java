package com.simpleproject.UserApi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Length.List({
            @Length(min =3, message = "The title must be at least 5 characters"),
            @Length(max = 50, message = "The title must be less than 50 characters")
    })
    private String title;

    @Length.List({
            @Length(min =3, message = "The description must be at least 5 characters"),
            @Length(max = 1000, message = "The description must be less than 50 characters")
    })
    private String description;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_fk")
    @JsonBackReference
    private User user;
}
