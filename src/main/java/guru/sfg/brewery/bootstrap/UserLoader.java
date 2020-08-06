package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            loadUserDataAndAuthorities();
        }
    }

    private void loadUserDataAndAuthorities() {
        Authority admin = Authority.builder().role("ADMIN").build();
        Authority user = Authority.builder().role("USER").build();
        Authority customer = Authority.builder().role("CUSTOMER").build();

        authorityRepository.save(admin);
        authorityRepository.save(user);
        authorityRepository.save(customer);

        User spring = User.builder().username("spring").password(passwordEncoder.encode("guru")).authority(admin).build();
        User user1 = User.builder().username("user1").password(passwordEncoder.encode("password")).authority(user).build();
        User scott = User.builder().username("scott").password(passwordEncoder.encode("tiger")).authority(customer).build();

        userRepository.save(spring);
        userRepository.save(user1);
        userRepository.save(scott);

        log.debug("users and authorities successfully loaded.");
    }
}
