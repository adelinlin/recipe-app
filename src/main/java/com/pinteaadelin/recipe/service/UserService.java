package com.pinteaadelin.recipe.service;

import com.pinteaadelin.recipe.exception.AlreadyExistsException;
import com.pinteaadelin.recipe.exception.UserRoleNotSupportedException;
import com.pinteaadelin.recipe.model.User;
import com.pinteaadelin.recipe.model.request.UserRequestDto;
import com.pinteaadelin.recipe.repository.RoleRepository;
import com.pinteaadelin.recipe.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;
import static java.util.Collections.singletonList;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("Not found user with username {0}", username)));
    }

    public User createUser(UserRequestDto userRequestDto, String roleName) {
        validateUserDetails(userRequestDto);
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        roleRepository.findRoleByName(roleName)
                .ifPresent(r -> user.setRoles(singletonList(r)));

        return userRepository.save(user);
    }

    private void validateUserDetails(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            throw new AlreadyExistsException(format("{0} is already used", userRequestDto.getUsername()));
        }
        if (roleRepository.findRoleByName(userRequestDto.getRole()).isEmpty()) {
            throw new UserRoleNotSupportedException(format("{0} is not supported. Only {1} are supported",
                    userRequestDto.getRole(), roleRepository.findAll()));
        }
    }

}
