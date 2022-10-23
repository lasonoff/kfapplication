package ru.yauroff.kfapplication.kfapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yauroff.kfapplication.kfapp.kfdtoconverters.KfRegistrationUserDtoConverter;
import ru.yauroff.kfapplication.kfapp.model.Role;
import ru.yauroff.kfapplication.kfapp.model.User;
import ru.yauroff.kfapplication.kfapp.repository.RoleRepository;
import ru.yauroff.kfapplication.kfapp.repository.UserRepository;
import ru.yauroff.kfapplication.kfapp.service.KafkaService;
import ru.yauroff.kfapplication.kfapp.service.UserService;
import ru.yauroff.kfapplication.kfapp.service.exceptions.UserRegistrationException;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    KafkaService kafkaService;

    @Autowired
    public void setKafkaService(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.getByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    @Transactional
    public void registerNewUser(User user) throws UserRegistrationException {
        Role role = roleRepository.getByName("ROLE_USER");
        if (role == null) {
            throw new UserRegistrationException("Not found user role!");
        }
        user.setRoles(List.of(role));
        User newUser = userRepository.save(user);
        KfRegistrationUserDto kfRegistrationUserDto = KfRegistrationUserDtoConverter.fromUser(newUser);
        kafkaService.flushUser(newUser.getId(), kfRegistrationUserDto);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", login));
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                    .map(r -> new SimpleGrantedAuthority(r.getName()))
                    .collect(Collectors.toList());
    }
}
