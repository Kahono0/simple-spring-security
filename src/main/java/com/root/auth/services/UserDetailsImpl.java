package com.root.auth.services;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.root.auth.models.User;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;

    }

    public static UserDetailsImpl build(User user) {
        // authority is 1, make it a list
        GrantedAuthority authority = new GrantedAuthority() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getAuthority() {
                return user.getRole().getName().toString();
            }
        };

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(authority);

        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(), authorities);
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }

}
