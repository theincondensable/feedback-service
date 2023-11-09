package io.incondensable.util;

import io.incondensable.business.model.auth.Role;
import io.incondensable.business.model.client.User;
import io.incondensable.business.service.model.user.UserDummy;
import io.incondensable.global.security.vo.FeedbackUserDetails;
import io.incondensable.global.security.vo.RoleEnum;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.List;
import java.util.Set;

/**
 * @author abbas
 */
public class FeedbackTestUtil {

    public static final Long ROLE_ADMIN_ID = 1L;
    public static final String ROLE_ADMIN_NAME = "ADMIN_USER";

    public static final Long ROLE_MANAGER_ID = 2L;
    public static final String ROLE_MANAGER_NAME = "MANAGER_USER";

    public static final Long ROLE_CUSTOMER_ID = 3L;
    public static final String ROLE_CUSTOMER_NAME = "CUSTOMER_USER";

    public static final Long ROLE_BIKER_ID = 2L;
    public static final String ROLE_BIKER_NAME = "BIKER_USER";

    public static final User ADMIN_USER = User.builder().id(ROLE_ADMIN_ID).firstname("ADMIN").lastname("USER").email("admin@mail.com").phoneNumber("09001000000").roles(Set.of(Role.builder().role(RoleEnum.ADMIN).build())).password(UserDummy.encodedPassword()).build();
    public static final User MANAGER_USER = User.builder().id(ROLE_MANAGER_ID).firstname("MANAGER").lastname("USER").email("manager@mail.com").phoneNumber("09002000000").roles(Set.of(Role.builder().role(RoleEnum.MANAGER).build())).password(UserDummy.encodedPassword()).build();
    public static final User CUSTOMER_USER = User.builder().id(ROLE_CUSTOMER_ID).firstname("CUSTOMER").lastname("USER").email("customer@mail.com").phoneNumber("09003000000").roles(Set.of(Role.builder().role(RoleEnum.CUSTOMER).build())).password(UserDummy.encodedPassword()).build();
    public static final User BIKER_USER = User.builder().id(ROLE_BIKER_ID).firstname("BIKER").lastname("USER").email("biker@mail.com").phoneNumber("09004000000").roles(Set.of(Role.builder().role(RoleEnum.BIKER).build())).password(UserDummy.encodedPassword()).build();

    private FeedbackTestUtil() {
    }

    public static void beforeSetup(Long roleId) {
        String roleName = setRoleName(roleId);

        SecurityContext sc = new SecurityContextImpl();
        sc.setAuthentication(
                new TestingAuthenticationToken(
                        new FeedbackUserDetails(roleId, "", "", roleName, List.of(new SimpleGrantedAuthority(roleName))),
                        null,
                        roleName
                )
        );
        SecurityContextHolder.setContext(sc);
    }

    public static void beforeSetupNoLoggedIn() {
        SecurityContext sc = new SecurityContextImpl();
        sc.setAuthentication(null);
        SecurityContextHolder.setContext(sc);
    }

    private static String setRoleName(Long roleId) {
        String roleName = null;
        if (roleId.equals(ROLE_ADMIN_ID))
            roleName = ROLE_ADMIN_NAME;
        if (roleId.equals(ROLE_MANAGER_ID))
            roleName = ROLE_MANAGER_NAME;
        if (roleId.equals(ROLE_CUSTOMER_ID))
            roleName = ROLE_CUSTOMER_NAME;
        if (roleId.equals(ROLE_BIKER_ID))
            roleName = ROLE_BIKER_NAME;

        return roleName;
    }

}
