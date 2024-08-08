package com.example.Redem.service;

import com.example.Redem.entity.JournalEntry;
import com.example.Redem.entity.UserEntity;
import com.example.Redem.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalentryrepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            UserEntity foundUser = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedJournal = journalentryrepository.save(journalEntry);
            foundUser.getJournalEntries().add(savedJournal);
            userService.saveUser(foundUser);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalentryrepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalentryrepository.findAll();
    }

    public Optional<JournalEntry> getByID(ObjectId id){
        return journalentryrepository.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id , String userName){
        try {
            UserEntity FoundUser = userService.findByUserName(userName);
            boolean removed = FoundUser.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(FoundUser);
                journalentryrepository.deleteById(id);
            }
        } catch (Exception e){
            throw new RuntimeException("Error occurred while deleting entry : " + e);
            }
        }

}
