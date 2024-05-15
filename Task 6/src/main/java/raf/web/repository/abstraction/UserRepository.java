package raf.web.repository.abstraction;

import raf.web.domain.User;
import raf.web.dto.UserDTO;

public interface UserRepository {

    User createUser(UserDTO userDto);

    User findUser(String username);

}
