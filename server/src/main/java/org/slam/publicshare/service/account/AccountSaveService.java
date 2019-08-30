package org.slam.publicshare.service.account;

import lombok.AllArgsConstructor;
import org.slam.publicshare.dto.account.AccountDto;
import org.slam.publicshare.mapper.account.AccountRoleMapper;
import org.slam.publicshare.mapper.account.AccountSaveMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class AccountSaveService {

    private final PasswordEncoder passwordEncoder;
    private final AccountSaveMapper accountSaveMapper;
    private final AccountRoleMapper accountRoleMapper;

    private static final Long DEFAULT_USER_ROLE = 1L;

    @Transactional
    public void save(AccountDto account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountSaveMapper.save(account);
        accountRoleMapper.save(account.getUsername(), DEFAULT_USER_ROLE);
    }

}