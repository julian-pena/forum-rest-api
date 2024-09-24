package com.alura.forum.service.user;

import com.alura.forum.mapper.UserMapper;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.repository.RoleRepository;
import com.alura.forum.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findUser_ReturnsUserDto() {
        UserEntity mockedUser = new UserEntity();
        mockedUser.setId(1L);  // Usar un valor real
        mockedUser.setName("Yuji Itadori");
        mockedUser.setEmail("yuji_itadori@gmail.com");
        mockedUser.setPassword("5A74008816jj-");
        mockedUser.setRegistrationDate(LocalDate.now());

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockedUser));
        when(userMapper.userToUserInfoDTO(mockedUser)).thenReturn(new UserInfoDTO(
                1L, "Yuji Itadori", "yuji_itadori@gmail.com", LocalDate.now().toString(), new HashSet<>()));

        UserInfoDTO returnedDTO = userService.getSingleUser(1L);

        assertThat(returnedDTO).isNotNull();
        assertThat(returnedDTO.name()).isEqualTo("Yuji Itadori");
        assertThat(returnedDTO.email()).isEqualTo("yuji_itadori@gmail.com");
        assertThat(returnedDTO.id()).isEqualTo(1L);
        assertThat(returnedDTO.courses()).isEmpty();
    }
}

