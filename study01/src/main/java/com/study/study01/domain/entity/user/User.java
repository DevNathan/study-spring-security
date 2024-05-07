package com.study.study01.domain.entity.user;

import com.study.study01.domain.base.Period;
import com.study.study01.domain.type.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

// User엔티티에 직접적으로 UserDetails를 붙여 확장 시킬 수도 있으나,
// User엔티티와 동일한 필드를 가진 클래스를 새로 생성해 그곳에 UserDetails를 붙혀 확장 시킬 수도 있다.
// 그러나 엔티티에 직접적으로 확장하게 될 경우 이 클래스는 두 가지의 책임을 가지게 된다.
// 1. 엔티티로서서의 역할과 2. UserDetails의 역할을 가지게 된다는 것이다.
// 이럴 경우 클래스내의 가독성이 심각하게 저하되는 결과가 초래된다.
// 클래스를 한 역할에는 한 역할만이 할당될 수 있도록 Security 전용 User클래스를 따로 만들어 운영하는 것이
// 더욱 바람직하다고 할 수 있겠다.
@Entity @Table(name = "TBL_USER")
@SequenceGenerator(name = "SEQ_USER_GENERATOR", sequenceName = "SEQ_USER", allocationSize = 1)
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class User extends Period {
    @Id @GeneratedValue(generator = "SEQ_USER_GENERATOR")
    @Column(name = "USER_ID")
    private Long id;
    private String email;
    private String password;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime lastLoginTime;

    @Builder
    public User(Long id, String email, String password, String nickname, Role role, LocalDateTime lastLoginTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.lastLoginTime = lastLoginTime;
    }
}
