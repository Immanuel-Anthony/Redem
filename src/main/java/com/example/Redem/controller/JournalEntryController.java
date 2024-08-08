package com.example.Redem.controller;

import com.example.Redem.entity.JournalEntry;
import com.example.Redem.entity.UserEntity;
import com.example.Redem.service.JournalEntryService;
import com.example.Redem.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserJournal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity FoundUser = userService.findByUserName(userName);
        List<JournalEntry> AllEntries = FoundUser.getJournalEntries();
        if(AllEntries != null && !AllEntries.isEmpty()){
            return new ResponseEntity<>(AllEntries , HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            UserEntity FoundUser = userService.findByUserName(userName);
            journalEntryService.saveEntry(myEntry , userName);
            return new ResponseEntity<>(myEntry , HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("get/{entryID}")
    public ResponseEntity<JournalEntry> entryById(@PathVariable ObjectId entryID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(entryID)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<JournalEntry> foundJournal = journalEntryService.getByID(entryID);
            if (foundJournal.isPresent()) {
                return new ResponseEntity<>(foundJournal.get(), HttpStatus.FOUND);
            }
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{entryID}")
    public ResponseEntity<JournalEntry> deleteEntryById(@PathVariable ObjectId entryID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryService.deleteById(entryID , userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId id , @RequestBody JournalEntry currentEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntryTemp = journalEntryService.getByID(id);
            if(journalEntryTemp.isPresent()){
                JournalEntry journalEntry = journalEntryTemp.get();
                journalEntry.setTitle(currentEntry.getTitle() != null && !currentEntry.getTitle().equals("") ? currentEntry.getTitle() : journalEntry.getTitle());
                journalEntry.setContent(currentEntry.getContent() != null && !currentEntry.getContent().equals("") ? currentEntry.getContent() : journalEntry.getContent());
                journalEntryService.saveEntry(journalEntry);
                return new ResponseEntity<>(journalEntry , HttpStatus.ACCEPTED);
            }
        }
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

}
