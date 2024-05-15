package raf.web.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.codec.digest.DigestUtils;
import raf.web.domain.User;
import raf.web.dto.UserDTO;
import raf.web.logger.LogHelper;
import raf.web.repository.abstraction.UserRepository;

import javax.inject.Inject;
import java.util.Date;

public class UserService {

    @Inject
    private UserRepository userRepository;

    public String login(String username, String password) {
        String hashedPassword = DigestUtils.sha256Hex(password);
        User user = this.userRepository.findUser(username);

        if(user == null || !user.getPassword().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24 * 60 * 60 * 1000);

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .sign(algorithm);
    }

    public User createUser(UserDTO userDTO) {
        LogHelper.getInstance().logInfo(UserService.class, "Creating user: " + userDTO.getUsername());
        return this.userRepository.createUser(userDTO);
    }

}
