package com.trading_simulator.backend.object.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "user")
@CompoundIndexes({
        @CompoundIndex(name = "email_idx", def = "{'email': 1}"),
        @CompoundIndex(name = "username_idx", def = "{'username': 1}")
})
public class User implements UserDetails {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email invalid")
    private String email; // Bắt buộc, username không cần nhập cũng được, nếu không có thì hệ thống sẽ tự tạo

    @Indexed(unique = true)
    @NotBlank(message = "Username is required")
    private String username; // không được phép đặt username giống email

    @NotBlank(message = "Password is required")
    private String password;

    @DBRef
    @NotNull(message = "RoleId is required")
    private Role role;

    @NotNull(message = "Active is required")
    private Boolean active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRole()));
    }

    @Override
    public String getUsername() {
        return email;
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
        return Boolean.TRUE.equals(active);
    }
}
