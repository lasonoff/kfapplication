package ru.yauroff.kfapplication.kfapp.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.yauroff.kfapplication.kfapp.model.Role;
import ru.yauroff.kfapplication.kfapp.model.User;
import ru.yauroff.kfapplication.kfapp.repository.RoleRepository;
import ru.yauroff.kfapplication.kfapp.repository.UserRepository;
import ru.yauroff.kfapplication.kfapp.service.KafkaService;
import ru.yauroff.kfapplication.kfapp.service.UserService;
import ru.yauroff.kfapplication.kfapp.service.exceptions.UserRegistrationException;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Mock
    private KafkaService kafkaServiceMock;

    private UserService instance;

    @BeforeEach
    public void before() {
        this.instance = new UserServiceImpl(this.userRepositoryMock, this.roleRepositoryMock, this.kafkaServiceMock);
    }

    @Test
    public void whenFindByEmailIsTriggered_thenFindUser() {
        User user = createUser("UserLogin", "UserPassword", "user@email.com", new ArrayList<>());
        when(this.userRepositoryMock.getByEmail("user@email.com"))
                .thenReturn(user);
        assertEquals(this.instance.findByEmail("user@email.com"), user);
    }

    @Test
    public void whenFindByEmailIsTriggeredForNull_thenNotFindUser() {
        when(this.userRepositoryMock.getByEmail(null))
                .thenReturn(null);
        assertNull(this.instance.findByEmail(null));
    }

    @Test
    public void whenFindByLoginIsTriggered_thenFindUser() {
        User user = createUser("UserLogin", "UserPassword", "user@email.com", new ArrayList<>());
        when(this.userRepositoryMock.getByLogin("UserLogin"))
                .thenReturn(user);
        assertEquals(this.instance.findByLogin("UserLogin"), user);
    }

    @Test
    public void whenFindByLoginIsTriggeredForNull_thenNotFindUser() {
        when(this.userRepositoryMock.getByLogin(null))
                .thenReturn(null);
        assertNull(this.instance.findByLogin(null));
    }

    @Test
    public void whenRegisterNewUserIsTriggered_thenRegisterNewUserAndFlushUser() throws UserRegistrationException {
        // when
        User user = createUser("UserLogin", "UserPassword", "user@email.com", new ArrayList<>());
        when(this.roleRepositoryMock.getByName("ROLE_USER")).thenReturn(new Role());
        when(this.userRepositoryMock.save(user)).thenReturn(user);

        // then
        User resUser = this.instance.registerNewUser(user);
        ArgumentCaptor<Long> argumentId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<KfRegistrationUserDto> argumentUserDto = ArgumentCaptor.forClass(KfRegistrationUserDto.class);
        verify(this.kafkaServiceMock).flushUser(argumentId.capture(), argumentUserDto.capture());
        assertEquals(resUser, user);
    }

    @Test
    public void whenRegisterNewUserIsTriggeredWithoutRole_thenException() throws UserRegistrationException {
        // when
        when(this.roleRepositoryMock.getByName("ROLE_USER")).thenReturn(null);

        // then
        UserRegistrationException thrown = Assertions.assertThrows(UserRegistrationException.class, () -> {
            this.instance.registerNewUser(null);
        });
        Assertions.assertEquals("Not found user role!", thrown.getMessage());
    }

    @Test
    public void whenLoadUserByUsernameIsTriggered_thenFindUser() {
        User user = createUser("UserLogin", "UserPassword", "user@email.com", new ArrayList<>());
        when(this.userRepositoryMock.getByLogin("UserLogin"))
                .thenReturn(user);
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(), new ArrayList<>());
        assertEquals(this.instance.loadUserByUsername("UserLogin"), userDetails);
    }

    @Test
    public void whenLoadUserByUsernameIsTriggeredForEmpty_thenException() throws UsernameNotFoundException {
        when(this.userRepositoryMock.getByLogin(""))
                .thenReturn(null);
        UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            this.instance.loadUserByUsername("");
        });
        Assertions.assertEquals("User '' not found", thrown.getMessage());
    }

    private User createUser(String login, String password, String mail, Collection<Role> roles) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(mail);
        user.setRoles(roles);
        return user;
    }

}