package org.barp.backend.Service;

import org.barp.backend.Model.MoneyAccount;
import org.barp.backend.Repository.MoneyAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MoneyAccountService {
    MoneyAccountRepository MoneyAccountRepository;

    @Autowired
    MoneyAccountService(MoneyAccountRepository MoneyAccountRepository){
        this.MoneyAccountRepository = MoneyAccountRepository;
    }
    public ResponseEntity<List<MoneyAccount>> getAllMoneyAccounts()
    {
        return new ResponseEntity<>(this.MoneyAccountRepository.findAll(), HttpStatus.OK);
    }
    public ResponseEntity<MoneyAccount> findMoneyAccountById(Long id)
    {
        try {
            Optional<MoneyAccount> MoneyAccount = this.MoneyAccountRepository.findById(id);
            if(MoneyAccount.isPresent()){
                return new ResponseEntity<>(MoneyAccount.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MoneyAccount not found");
            }
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That MoneyAccount already exists");
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred deleting MoneyAccount");
        }
    }

    /**
     * Add a MoneyAccount to the database.
     * <br><br>
     * pre-condition #1: MoneyAccount does not exist <br>
     * post-condition #1: MoneyAccount is added to the database, return MoneyAccount in Optional
     * <br><br>
     * pre-condition #2: email already exists<br>
     * post-condition #2 request rejected
     * @param MoneyAccount to add to the database
     * @return a {@link ResponseEntity<MoneyAccount>}
     */
    public ResponseEntity<?> addMoneyAccount(MoneyAccount MoneyAccount)
    {
        try {
            return new ResponseEntity<>(this.MoneyAccountRepository.save(MoneyAccount), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That MoneyAccount already exists");
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred deleting MoneyAccount");
        }
        //return new ServiceResult<MoneyAccount>(ServiceResult.ServiceCode.SERVER_ERROR, "Add MoneyAccount failed");
    }

    /**
     * Update MoneyAccount in the database.
     *
     * <br><br>
     * pre-condition #1: MoneyAccount does not exist
     * <br>
     * post-condition #1: new MoneyAccount is added
     * <br><br>
     * pre-condition #2: MoneyAccount does exist
     * <br>
     * post-condition #2: the MoneyAccount record is updated
     *
     * @param id the id of the MoneyAccount account
     * @param MoneyAccount a MoneyAccount account to save to the database
     * @return a {@link ResponseEntity<MoneyAccount>}
     */
    public ResponseEntity<?> updateMoneyAccount(Long id, MoneyAccount MoneyAccount)
    {
        // TODO: test if all fields of the MoneyAccount input param need to be filled
        MoneyAccount.setAccountId(id);
        try {
            return new ResponseEntity<>(this.MoneyAccountRepository.save(MoneyAccount), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while updating MoneyAccount");
        }
    }

    /**
     * Delete a {@link MoneyAccount} from database, returning the deleted MoneyAccount.
     * <br><br>
     * pre-condition #1: MoneyAccount exists
     * <br>
     * post-condition #1: MoneyAccount is deleted from the database
     * <br><br>
     * pre-condition #2: MoneyAccount account does not exist
     * <br>
     * post-condition #2: no change
     *
     * @param id of the MoneyAccount account
     * @return a {@link ResponseEntity} object
     */
    public ResponseEntity<?> deleteMoneyAccount(Long id)
    {
        try {
            Optional<MoneyAccount> MoneyAccount = this.MoneyAccountRepository.findById(id);
            if(MoneyAccount.isPresent())
            {
                this.MoneyAccountRepository.deleteById(id);
                return new ResponseEntity<>(MoneyAccount, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That MoneyAccount does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while deleting MoneyAccount");
        }
    }

    public ResponseEntity<List<MoneyAccount>> getMoneyAccountByUserId(Long accountId) {
        try {
            Optional<List<MoneyAccount>> MoneyAccount = this.MoneyAccountRepository.findMoneyAccountByUserUserId(accountId);
            if(MoneyAccount.isPresent())
            {
                return new ResponseEntity<>(MoneyAccount.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That MoneyAccount {"+accountId+"} does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting MoneyAccount by id");
        }
    }
}
