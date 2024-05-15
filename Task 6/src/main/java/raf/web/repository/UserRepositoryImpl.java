package raf.web.repository;

import org.apache.commons.codec.digest.DigestUtils;
import raf.web.domain.User;
import raf.web.dto.UserDTO;
import raf.web.logger.LogHelper;
import raf.web.repository.abstraction.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl extends SQLRepository implements UserRepository {

    @Override
    public User createUser(UserDTO userDto) {
        LogHelper.getInstance().logInfo(UserRepositoryImpl.class, "Creating user: " + userDto.getUsername());
        String hashedPassword = DigestUtils.sha256Hex(userDto.getPassword());
        String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
        executeUpdate(query, userDto.getUsername(), hashedPassword);

        return findUser(userDto.getUsername());
    }

    @Override
    public User findUser(String username) {
        LogHelper.getInstance().logInfo(UserRepositoryImpl.class, "Finding user: " + username);
        String query = "SELECT * FROM Users WHERE username = ?";

        return executeQuery(query, this::mapToUser, username).stream()
                .findFirst().orElse(null);
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        LogHelper.getInstance().logInfo(UserRepositoryImpl.class, "Mapping user from result set: " + resultSet.getString("username"));
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password")
        );
    }

}
