package org.barp.backend.Service;

import org.barp.backend.Model.VerificationKey;
import org.barp.backend.Repository.VerificationKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VerificationKeyService {
    VerificationKeyRepository VerificationKeyRepository;

    @Autowired
    VerificationKeyService(VerificationKeyRepository VerificationKeyRepository){
        this.VerificationKeyRepository = VerificationKeyRepository;
    }
    public ResponseEntity<List<VerificationKey>> getAllVerificationKeys()
    {
        return new ResponseEntity<>(this.VerificationKeyRepository.findAll(), HttpStatus.OK);
    }
    public ResponseEntity<VerificationKey> findVerificationKeyById(Long id)
    {
        try {
            Optional<VerificationKey> VerificationKey = this.VerificationKeyRepository.findById(id);
            if(VerificationKey.isPresent()){
                return new ResponseEntity<>(VerificationKey.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "VerificationKey not found");
            }
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That VerificationKey already exists");
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred deleting VerificationKey");
        }
    }

    /**
     * Add a VerificationKey to the database.
     * <br><br>
     * pre-condition #1: VerificationKey does not exist <br>
     * post-condition #1: VerificationKey is added to the database, return VerificationKey in Optional
     * <br><br>
     * pre-condition #2: email already exists<br>
     * post-condition #2 request rejected
     * @param VerificationKey to add to the database
     * @return a {@link ResponseEntity<VerificationKey>}
     */
    public ResponseEntity<?> addVerificationKey(VerificationKey VerificationKey)
    {
        try {
            return new ResponseEntity<>(this.VerificationKeyRepository.save(VerificationKey), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That VerificationKey already exists");
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred deleting VerificationKey");
        }
        //return new ServiceResult<VerificationKey>(ServiceResult.ServiceCode.SERVER_ERROR, "Add VerificationKey failed");
    }

    /**
     * Update VerificationKey in the database.
     *
     * <br><br>
     * pre-condition #1: VerificationKey does not exist
     * <br>
     * post-condition #1: new VerificationKey is added
     * <br><br>
     * pre-condition #2: VerificationKey does exist
     * <br>
     * post-condition #2: the VerificationKey record is updated
     *
     * @param id the id of the VerificationKey account
     * @param VerificationKey a VerificationKey account to save to the database
     * @return a {@link ResponseEntity<VerificationKey>}
     */
    public ResponseEntity<?> updateVerificationKey(Long id, VerificationKey VerificationKey)
    {
        // TODO: test if all fields of the VerificationKey input param need to be filled
        VerificationKey.setKeyId(id);
        try {
            return new ResponseEntity<>(this.VerificationKeyRepository.save(VerificationKey), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while updating VerificationKey");
        }
    }

    /**
     * Delete a {@link VerificationKey} from database, returning the deleted VerificationKey.
     * <br><br>
     * pre-condition #1: VerificationKey exists
     * <br>
     * post-condition #1: VerificationKey is deleted from the database
     * <br><br>
     * pre-condition #2: VerificationKey account does not exist
     * <br>
     * post-condition #2: no change
     *
     * @param id of the VerificationKey account
     * @return a {@link ResponseEntity} object
     */
    public ResponseEntity<?> deleteVerificationKey(Long id)
    {
        try {
            Optional<VerificationKey> VerificationKey = this.VerificationKeyRepository.findById(id);
            if(VerificationKey.isPresent())
            {
                this.VerificationKeyRepository.deleteById(id);
                return new ResponseEntity<>(VerificationKey, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That VerificationKey does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while deleting VerificationKey");
        }
    }
}
