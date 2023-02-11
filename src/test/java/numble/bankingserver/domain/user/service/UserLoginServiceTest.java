package numble.bankingserver.domain.user.service;

import numble.bankingserver.domain.user.dto.request.UserLoginRequest;
import numble.bankingserver.domain.user.dto.response.UserLoginResponse;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.enums.Gender;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserLoginServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserLoginService userLoginService;

    @Test
    @Transactional
    @DisplayName("로그인 성공")
    public void userLoginSuccess() {

        User user = User.builder()
                .id("aaaa")
                .password("aaaa")
                .name("김현욱")
                .phoneNumber("010-2988-9331")
                .email("hyeonwook9@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userRepository.save(user);

        UserLoginRequest request = UserLoginRequest.builder()
                .id("aaaa")
                .password("aaaa")
                .build();

        UserLoginResponse response = userLoginService.userLogin(request);
        System.out.println(response.getAccessToken());

        Assertions.assertTrue(!response.getAccessToken().isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("로그인 실패")
    public void userLoginFail() {

        User user = User.builder()
                .id("aaaa")
                .password("aaaa")
                .name("김현욱")
                .phoneNumber("010-2988-9331")
                .email("hyeonwook9@naver.com")
                .address("경상남도 통영시")
                .gender(Gender.MAN)
                .birthYear("1998-11-17")
                .build();

        userRepository.save(user);

        UserLoginRequest request = UserLoginRequest.builder()
                .id("aaa")
                .password("aaaa")
                .build();

        Assertions.assertThrows(BankingException.class, () -> {
            userLoginService.userLogin(request);
        });

    }
}