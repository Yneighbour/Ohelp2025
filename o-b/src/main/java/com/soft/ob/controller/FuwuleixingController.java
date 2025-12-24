package com.soft.ob.controller;

import com.soft.ob.entity.Fuwuleixing;
import com.soft.ob.service.FuwuleixingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fuwuleixing")
@CrossOrigin(origins = "*")
public class FuwuleixingController {
    
    @Autowired
    private FuwuleixingService fuwuleixingService;
    
    @GetMapping
    public ResponseEntity<List<Fuwuleixing>> getAllFuwuleixing() {
        List<Fuwuleixing> fuwuleixingList = fuwuleixingService.getAllFuwuleixing();
        return new ResponseEntity<>(fuwuleixingList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Fuwuleixing> getFuwuleixingById(@PathVariable Long id) {
        Optional<Fuwuleixing> fuwuleixing = fuwuleixingService.getFuwuleixingById(id);
        return fuwuleixing.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Fuwuleixing> createFuwuleixing(@RequestBody Fuwuleixing fuwuleixing) {
        Fuwuleixing savedFuwuleixing = fuwuleixingService.saveFuwuleixing(fuwuleixing);
        return new ResponseEntity<>(savedFuwuleixing, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Fuwuleixing> updateFuwuleixing(@PathVariable Long id, @RequestBody Fuwuleixing fuwuleixing) {
        try {
            Fuwuleixing updatedFuwuleixing = fuwuleixingService.updateFuwuleixing(id, fuwuleixing);
            return new ResponseEntity<>(updatedFuwuleixing, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuwuleixing(@PathVariable Long id) {
        try {
            fuwuleixingService.deleteFuwuleixing(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}