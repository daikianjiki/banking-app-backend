package org.barp.backend.Service;

import org.barp.backend.Model.Transaction;
import org.barp.backend.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    TransactionRepository TransactionRepository;

    @Autowired
    TransactionService(TransactionRepository TransactionRepository){
        this.TransactionRepository = TransactionRepository;
    }
    public ResponseEntity<List<Transaction>> getAllTransactions()
    {
        return new ResponseEntity<>(this.TransactionRepository.findAll(), HttpStatus.OK);
    }
    public ResponseEntity<Transaction> findTransactionById(Long id)
    {
        try {
            Optional<Transaction> Transaction = this.TransactionRepository.findById(id);
            if(Transaction.isPresent()){
                return new ResponseEntity<>(Transaction.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
            }
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That Transaction already exists");
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred deleting Transaction");
        }
    }

    /**
     * Add a Transaction to the database.
     * <br><br>
     * pre-condition #1: Transaction does not exist <br>
     * post-condition #1: Transaction is added to the database, return Transaction in Optional
     * <br><br>
     * pre-condition #2: email already exists<br>
     * post-condition #2 request rejected
     * @param Transaction to add to the database
     * @return a {@link ResponseEntity<Transaction>}
     */
    public ResponseEntity<?> addTransaction(Transaction Transaction)
    {
        try {
            return new ResponseEntity<>(this.TransactionRepository.save(Transaction), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That Transaction already exists");
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred deleting Transaction");
        }
        //return new ServiceResult<Transaction>(ServiceResult.ServiceCode.SERVER_ERROR, "Add Transaction failed");
    }

    /**
     * Update Transaction in the database.
     *
     * <br><br>
     * pre-condition #1: Transaction does not exist
     * <br>
     * post-condition #1: new Transaction is added
     * <br><br>
     * pre-condition #2: Transaction does exist
     * <br>
     * post-condition #2: the Transaction record is updated
     *
     * @param id the id of the Transaction account
     * @param Transaction a Transaction account to save to the database
     * @return a {@link ResponseEntity<Transaction>}
     */
    public ResponseEntity<?> updateTransaction(Long id, Transaction Transaction)
    {
        // TODO: test if all fields of the Transaction input param need to be filled
        Transaction.setTransactionId(id);
        try {
            return new ResponseEntity<>(this.TransactionRepository.save(Transaction), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while updating Transaction");
        }
    }

    /**
     * Delete a {@link Transaction} from database, returning the deleted Transaction.
     * <br><br>
     * pre-condition #1: Transaction exists
     * <br>
     * post-condition #1: Transaction is deleted from the database
     * <br><br>
     * pre-condition #2: Transaction account does not exist
     * <br>
     * post-condition #2: no change
     *
     * @param id of the Transaction account
     * @return a {@link ResponseEntity} object
     */
    public ResponseEntity<?> deleteTransaction(Long id)
    {
        try {
            Optional<Transaction> Transaction = this.TransactionRepository.findById(id);
            if(Transaction.isPresent())
            {
                this.TransactionRepository.deleteById(id);
                return new ResponseEntity<>(Transaction, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That Transaction does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while deleting Transaction");
        }
    }
}

