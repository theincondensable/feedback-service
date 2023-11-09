package io.incondensable.business.service;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Biker;
import io.incondensable.business.repository.BikerRepository;
import io.incondensable.business.service.model.biker.BikerDummy;
import io.incondensable.global.exception.BusinessException;
import io.incondensable.util.FeedbackTestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Biker Service Unit Tests")
class BikerServiceTest {

    private final UserService userService = Mockito.mock(UserService.class);
    private final BikerRepository bikerRepository = Mockito.mock(BikerRepository.class);
    private final FeedbackService feedbackService = Mockito.mock(FeedbackService.class);

    private final BikerService SUT = new BikerService(userService, bikerRepository, feedbackService);

    @BeforeAll
    public static void setup() {
        FeedbackTestUtil.beforeSetup(FeedbackTestUtil.ROLE_BIKER_ID);
    }

    @Test
    @DisplayName("GIVEN Biker is Logged-in WHEN get Biker's ID using its held Logged-in UserDetails is called THEN the Biker is returned")
    public void test1() {
        //GIVEN
        User expectedUser = FeedbackTestUtil.BIKER_USER;
        when(userService.getUserById(expectedUser.getId())).thenReturn(expectedUser);
        when(bikerRepository.findBikerByUser_Id(expectedUser.getId())).thenReturn(Optional.of(BikerDummy.createBiker(expectedUser)));

        //WHEN
        Biker actual = SUT.getBikerByLoggedInUser();

        //THEN
        assertThat(actual.getUser(), is(expectedUser));
    }

}