package org.barp.backend.Service;

import org.barp.backend.Model.MoneyAccount;
import org.barp.backend.Model.User;
import org.barp.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public ServiceResult<List<User>> getAllUsers()
    {
        return new ServiceResult<>(this.userRepository.findAll(), ServiceResult.ServiceCode.SUCCESS);
    }
    public Optional<User> findUserById(Long id)
    {
        return this.userRepository.findById(id);
    }

    /**
     * Add a user to the database.
     * <br><br>
     * pre-condition #1: user does not exist <br>
     * post-condition #1: user is added to the database, return user in Optional
     * <br><br>
     * pre-condition #2: email already exists<br>
     * post-condition #2 request rejected
     * @param user to add to the database
     * @return an {@link ServiceResult<User>} containing a User on success
     */
    public ServiceResult<User> addUser(User user)
    {
        try {
            return new ServiceResult<>(this.userRepository.save(user), ServiceResult.ServiceCode.SUCCESS);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That user already exists");
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred deleting user");
        }
        //return new ServiceResult<User>(ServiceResult.ServiceCode.SERVER_ERROR, "Add user failed");
    }

    /**
     * Update user in the database.
     *
     * <br><br>
     * pre-condition #1: user does not exist
     * <br>
     * post-condition #1: new user is added
     * <br><br>
     * pre-condition #2: user does exist
     * <br>
     * post-condition #2: the user record is updated
     *
     * @param id the id of the user account
     * @param user a user account to save to the database
     * @return an {@link ServiceResult<User>} containing the {@link User} on success
     */
    public ServiceResult<User> updateUser(Long id, User user)
    {
        // TODO: test if all fields of the User input param need to be filled
        // TODO: add message and statusCode to result
        user.setUserId(id);
        try {
            return new ServiceResult<>(this.userRepository.save(user), ServiceResult.ServiceCode.SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ServiceResult<User>(
                ServiceResult.ServiceCode.SERVER_ERROR,
                "Update user failed"
        );
    }

    /**
     * Delete a {@link User} from database, returning the deleted user.
     * <br><br>
     * pre-condition #1: user exists
     * <br>
     * post-condition #1: user is deleted from the database
     * <br><br>
     * pre-condition #2: user account does not exist
     * <br>
     * post-condition #2: no change
     *
     * @param id of the user account
     * @return a {@link ServiceResult} object
     */
    public ServiceResult<User> deleteUser(Long id)
    {

        try {
            /*
             * findById returns an optional.
             *
             * If the optional contains a value the
             * map code block executes. It deletes the
             * user and returns it via a ServiceResult
             *
             * If the optional does not contain a value
             * the orElse section executes and returns
             * an empty ServiceResult.
             */
            return this.userRepository.findById(id)
                    .map(user -> {
                        this.userRepository.delete(user);
                        return new ServiceResult<>(user);
                    }).orElse(new ServiceResult<>(
                            ServiceResult.ServiceCode.CLIENT_ERROR,
                            "That user does not exist")
                    );

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred deleting user");
        }
        // this runs if an exception is encountered
        // it returns an empty result
        // TODO: return a result that has description and status code
        // return new ServiceResult<User>(ServiceResult.ServiceCode.SERVER_ERROR);
    }
}
