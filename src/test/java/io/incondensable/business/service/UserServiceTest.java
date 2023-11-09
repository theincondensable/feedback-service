package io.incondensable.business.service;

import io.incondensable.business.model.auth.Role;
import io.incondensable.business.model.client.Address;
import io.incondensable.business.model.client.User;
import io.incondensable.business.repository.RoleRepository;
import io.incondensable.business.repository.UserRepository;
import io.incondensable.business.service.model.user.SignupRequestDtoDummy;
import io.incondensable.business.service.model.user.UserDummy;
import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.security.vo.RoleEnum;
import io.incondensable.mapper.AddressMapper;
import io.incondensable.mapper.UserMapper;
import io.incondensable.util.FeedbackTestUtil;
import io.incondensable.web.dto.auth.request.SignupRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("User Service Unit Tests")
class UserServiceTest {

    private final UserMapper userMapper = mock(UserMapper.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final AddressMapper addressMapper = mock(AddressMapper.class);

    private final UserService SUT = new UserService(roleRepository, userMapper, userRepository);

    @BeforeAll
    public static void setup() {
        FeedbackTestUtil.beforeSetup(FeedbackTestUtil.ROLE_BIKER_ID);
    }

    @Test
    @DisplayName("GIVEN UserID WHEN Finding User with the Id NOT FOUND THEN an Exception is thrown.")
    public void testUserWithIdNotFound() {
        //GIVEN
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //WHEN
        BusinessException actual = assertThrows(BusinessException.class, () -> SUT.getUserById(userId));

        //THEN
        assertThat(actual.getErrorDetails().getMessage(), is("user.not.found.with.given.id"));
    }

    @Test
    @DisplayName("GIVEN UserID WHEN Finding User with the Id is found THEN the User is Returned.")
    public void testUserIsReturnedByGivenId() {
        //GIVEN
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(FeedbackTestUtil.CUSTOMER_USER));

        //WHEN
        User actualUser = SUT.getUserById(userId);

        //THEN
        assertThat(actualUser.getId(), is(actualUser.getId()));
        assertThat(actualUser.getFirstname(), is(actualUser.getFirstname()));
    }

    @Test
    @DisplayName("GIVEN User Email WHEN Finding User with the Email NOT FOUND THEN an Exception is thrown.")
    public void testUserWithEmailNotFound() {
        //GIVEN
        String email = "email@mail.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        //WHEN
        BusinessException actual = assertThrows(BusinessException.class, () -> SUT.getUserByEmail(email));

        //THEN
        assertThat(actual.getErrorDetails().getMessage(), is("user.not.found.with.given.email"));
    }

    @Test
    @DisplayName("GIVEN User Email WHEN Finding User with the Email is found THEN the User is Returned.")
    public void testUserIsReturnedByGivenEmail() {
        //GIVEN
        String email = FeedbackTestUtil.CUSTOMER_USER.getEmail();
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(FeedbackTestUtil.CUSTOMER_USER));

        //WHEN
        User actualUser = SUT.getUserByEmail(email);

        //THEN
        assertThat(actualUser.getId(), is(actualUser.getId()));
        assertThat(actualUser.getEmail(), is(actualUser.getEmail()));
    }

    @Test
    @DisplayName("GIVEN User Email WHEN check Existence of User Email is Duplicated THEN True is returned.")
    public void testEnteredEmailIsDuplicate() {
        //GIVEN
        String email = "email@mail.com";
        when(userRepository.existsUserByEmail(email)).thenReturn(true);

        //WHEN
        boolean actual = SUT.isEmailDuplicate(email);

        //THEN
        assertThat(actual, is(Boolean.TRUE));
    }

    @Test
    @DisplayName("GIVEN User Email WHEN check Existence of User Email is Unique THEN False is returned.")
    public void testEnteredEmailIsUnique() {
        //GIVEN
        String email = "email@mail.com";
        when(userRepository.existsUserByEmail(email)).thenReturn(false);

        //WHEN
        boolean actual = SUT.isEmailDuplicate(email);

        //THEN
        assertThat(actual, is(Boolean.FALSE));
    }

    @Test
    @DisplayName("GIVEN User Create Request WHEN create User is called THEN the Created User matching Request Information is returned.")
    public void testCreateUser() {
        //GIVEN
        String expectedPassword = UserDummy.encodedPassword();
        User expectedUser = FeedbackTestUtil.CUSTOMER_USER;
        expectedUser.setPassword(null);
        SignupRequestDto request = SignupRequestDtoDummy.create(expectedUser);

        when(userMapper.dtoToEntity(request.getUser())).thenReturn(expectedUser);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        //WHEN
        User actual = SUT.createUserOnSignup(request, expectedPassword);

        //THEN
        verify(userRepository, times(1)).save(any());
        assertThat(actual.getPassword(), is(expectedPassword));
        assertThat(actual.getEmail(), is(expectedUser.getEmail()));
        assertThat(actual.getRoles(), is(expectedUser.getRoles()));
    }

}