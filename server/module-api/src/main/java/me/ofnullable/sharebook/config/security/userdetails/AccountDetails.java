package me.ofnullable.sharebook.config.security.userdetails;

import me.ofnullable.sharebook.account.domain.Account;
import me.ofnullable.sharebook.account.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDetails extends User {

    private static final String ROLE_PREFIX = "ROLE_";

    private Account account;

    public AccountDetails(Account account) {
        super(account.getEmail().getAddress(), account.getPassword(), getAuthorities(account.getRoles()));
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }

    public Account refresh(Account account) {
        this.account = account;
        return account;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(ROLE_PREFIX + r.getName()))
                .collect(Collectors.toList());
    }

}