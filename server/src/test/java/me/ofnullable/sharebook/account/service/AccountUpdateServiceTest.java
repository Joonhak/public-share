package me.ofnullable.sharebook.account.service;

import me.ofnullable.sharebook.account.domain.Account;
import me.ofnullable.sharebook.account.domain.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class AccountUpdateServiceTest {

    @Mock
    private AccountUpdateService accountUpdateService;

    private final Account account = Account.builder().email(Email.of("test@asd.com")).name("test").password("test").build();
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    @DisplayName("비밀번호 업데이트")
    void update_password() {
        given(accountUpdateService.updatePassword(any(Long.class), anyString()))
                .willReturn(account);

        var result = accountUpdateService.updatePassword(1L, "test");

        assertTrue(passwordEncoder.matches("test", result.getPassword()));
    }

}
