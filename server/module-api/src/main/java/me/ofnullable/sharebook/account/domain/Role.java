package me.ofnullable.sharebook.account.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false)
    private Account account;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Builder
    public Role(RoleName name, Account account) {
        this.name = name;
        this.account = account;
    }

}
