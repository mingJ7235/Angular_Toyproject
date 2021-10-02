package com.rest.angular_api.repo;

import com.rest.angular_api.entity.User;
import com.rest.angular_api.repository.UserJpaRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.Option;
import javax.validation.constraints.AssertTrue;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserJpaRepoTest {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenFindByUid_thenReturnUser() {
        String uid = "joshuara7235@gmail.com";
        String name = "minjae";

        //given
        userJpaRepo.save(User.builder()
                    .uid(uid)
                    .password(passwordEncoder.encode("1234"))
                    .name(name)
                    .roles(Collections.singletonList("ROLE_USER"))
                .build());
        //when
        Optional<User> user = userJpaRepo.findByUid(uid);
        //then
        assertTrue(user.isPresent());
        assertEquals(user.get().getName(), name);
    }

}
