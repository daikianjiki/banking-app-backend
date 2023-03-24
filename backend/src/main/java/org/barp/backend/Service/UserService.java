package org.barp.backend.Service;

import org.barp.backend.Model.User;
import org.barp.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> getAllUsers()
    {
        return new ResponseEntity<>(this.userRepository.findAll(), HttpStatus.OK);
    }
    public ResponseEntity<User> findUserById(Long id)
    {
        try {
            Optional<User> user = this.userRepository.findById(id);
            if(user.isPresent()){
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That user already exists");
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred deleting user");
        }
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
     * @return a {@link ResponseEntity<User>}
     */
    public ResponseEntity<?> addUser(User user)
    {
        try {
            return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
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
     * @return a {@link ResponseEntity<User>}
     */
    public ResponseEntity<?> updateUser(Long id, User user)
    {
        // TODO: test if all fields of the User input param need to be filled
        user.setUserId(id);
        try {
            return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while updating user");
        }
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
     * @return a {@link ResponseEntity} object
     */
    public ResponseEntity<?> deleteUser(Long id)
    {
        try {
            Optional<User> user = this.userRepository.findById(id);
            if(user.isPresent())
            {
                this.userRepository.deleteById(id);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That user does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while deleting user");
        }
    }

    public ResponseEntity<?> loginUser(User user) {
        try {
            return new ResponseEntity<User>(
                    this.userRepository.findUserByUsernameAndPassword(
                            user.getUsername(),
                            user.getPassword()).orElseThrow(
                                    () -> new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "Username/pass combo does not exist"
                                    )
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred during login");
        }
    }
}
