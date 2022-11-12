package ru.yauroff.kfapplication.kfapp.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yauroff.kfapplication.kfapp.model.User;
import ru.yauroff.kfapplication.kfapp.repository.RoleRepository;
import ru.yauroff.kfapplication.kfapp.repository.UserRepository;
import ru.yauroff.kfapplication.kfapp.service.KafkaService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    @Mock
    private KafkaService mockKafkaService;


    @Test
    public void loadUserByUsername() {
        User user = new User();
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setEmail("user@email.com");
        user.setRoles(new ArrayList<>());
        when(mockUserRepository.getByLogin("UserLogin"))
                .thenReturn(user);
        UserServiceImpl service = new UserServiceImpl(this.mockUserRepository, null, null);
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(), new ArrayList<>());
        assertEquals(service.loadUserByUsername("UserLogin"), userDetails);
    }

}