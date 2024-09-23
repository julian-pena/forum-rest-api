package com.alura.forum;

import com.alura.forum.mapper.UserMapper;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.repository.RoleRepository;
import com.alura.forum.repository.UserRepository;
import com.alura.forum.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class ForumApplicationTests {

}
