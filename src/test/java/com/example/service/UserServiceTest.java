package com.example.service;


import com.example.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@ExtendWith(
        MockitoExtension.class
)
class UserServiceTest {


    @Captor
    private ArgumentCaptor<Long> argumentCaptor;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;


    @BeforeEach
    void init() {
        System.out.println("Before each :" + this);
    }

    @Test
    void usersNotEmptyIfUserAdded() {
//        userService.create(new UserDTO());
//        userService.create(new UserDTO());

//        List<UserDTO> users = userService.getAll(1L);
//        assertEquals(2, users.size());
//    }
//
//    @Test
//    void throwExceptionIfUserByIdAndCompanyIdNull() {
//       doThrow(UserBadRequestException.class)
//                .when(userRepository).findByIdAndCompanyId(null, null);
//
//        assertThrows(UserBadRequestException.class, () -> userService.get(1L,1L));
//    }

    }
}
