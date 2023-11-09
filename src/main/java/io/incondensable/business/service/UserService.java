package io.incondensable.business.service;

import io.incondensable.business.exceptions.user.UserNotFoundWithEmail;
import io.incondensable.business.exceptions.user.UserNotFoundWithId;
import io.incondensable.business.model.auth.Role;
import io.incondensable.business.model.client.User;
import io.incondensable.business.repository.UserRepository;
import io.incondensable.business.repository.RoleRepository;
import io.incondensable.global.security.vo.RoleEnum;
import io.incondensable.mapper.UserMapper;
import io.incondensable.web.dto.auth.request.SignupRequestDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author abbas
 */
@Service
public class UserService {

    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserService(RoleRepository roleRepository, UserMapper userMapper, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new UserNotFoundWithId(userId);
                });
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    throw new UserNotFoundWithEmail(email);
                });
    }

    /**
     * <p>When there is the possibility of Adding a new User with duplicated E-Mail Address, this
     * Method will help you to find out whether there is E-Mail duplicate or not.</p>
     *
     * @param email E-Mail Address to check its duplication
     * @return true if there is duplicate, otherwise returns false
     */
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsCustomerByEmail(email);
    }

    /**
     * <p>This method adds all the Roles the User should have, Sets the Encoded Password for the User
     * And also saves the User instance in Database.</p>
     *
     * @param req             contains the User information to be created
     * @param encodedPassword
     * @return
     */
    public User createUserOnSignup(SignupRequestDto req, String encodedPassword) {
        Set<Role> roles = new HashSet<>();
        req.getUser().getRoles().forEach(
                r -> roles.add(roleRepository.findAllByRole(RoleEnum.valueOf(r)))
        );

        User user = userMapper.dtoToEntity(req.getUser());
        user.setPassword(encodedPassword);
        user.setRoles(roles);

        return userRepository.save(user);
    }

}
