package com.rest.angular_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * USER Entity
 *
 * - SpringSecurity 보안을 적용하기 위해 UserDetails interface를 상속받아 추가정보를 override해줘야한다.
 * - roles는 회원이 가지고 있는 권한 정보이고, 가입했을 때는 기본적으로 "ROLE_USER:가 세팅된다.
 * - roles를 collection으로 한 이유는, 권한은 회원당 여러개가 세팅될 수 잇으므로 이렇게 선언한다.
 */

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long msrl;

    @Column (nullable = false, unique = true, length = 30)
    private String uid;

    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY) //JSON으로 출력안할 데이터이므로 쓰기전용으로 옵션을 바꿔준다.
    @Column (length = 100)
    private String password;

    @Column (nullable = false, length = 100)
    private String name;

    @Column (length = 100)
    private String provider;

    @ElementCollection (fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    //- getUsername은 security에서 사용하는 회원 구분 id인데, 여기서는 uid로 변경한다.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    /**
     * Securyti에서 사용하는 회원 상태 값 override method.
     * 여기서는 모두 사용안하므로 return 값이 모두 true이다.
     * password와 마찬가지로, json 결과로 출력안할 데이터는 @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)로 선언함
     */

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
