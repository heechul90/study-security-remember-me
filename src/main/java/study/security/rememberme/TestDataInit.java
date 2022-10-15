package study.security.rememberme;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.security.rememberme.core.domain.User;
import study.security.rememberme.core.domain.UserAuthority;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.memberInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        @PersistenceContext
        private final EntityManager em;

        @Autowired
        PasswordEncoder passwordEncoder;

        public void memberInit() {

            User admin = User.builder()
                    .email("admin@naver.com")
                    .password(passwordEncoder.encode("1234"))
                    .build();
            em.persist(admin);
            em.persist(new UserAuthority(admin.getUserId(), "ROLE_ADMIN"));


            User spring = User.builder()
                    .email("spring@naver.com")
                    .password(passwordEncoder.encode("1234"))
                    .build();
            em.persist(spring);
            em.persist(new UserAuthority(spring.getUserId(), "ROLE_USER"));
        }
    }
}
