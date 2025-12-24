package com.soft.ob.controller;

import com.soft.ob.entity.Fuwugoumai;
import com.soft.ob.service.FuwugoumaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fuwugoumai")
@CrossOrigin(origins = "*")
public class FuwugoumaiController {
    
    @Autowired
    private FuwugoumaiService fuwugoumaiService;
    
    @GetMapping
    public ResponseEntity<List<Fuwugoumai>> getAllFuwugoumai() {
        List<Fuwugoumai> fuwugoumaiList = fuwugoumaiService.getAllFuwugoumai();
        return new ResponseEntity<>(fuwugoumaiList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Fuwugoumai> getFuwugoumaiById(@PathVariable Long id) {
        Optional<Fuwugoumai> fuwugoumai = fuwugoumaiService.getFuwugoumaiById(id);
        return fuwugoumai.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Fuwugoumai> createFuwugoumai(@RequestBody Fuwugoumai fuwugoumai) {
        Fuwugoumai savedFuwugoumai = fuwugoumaiService.saveFuwugoumai(fuwugoumai);
        return new ResponseEntity<>(savedFuwugoumai, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Fuwugoumai> updateFuwugoumai(@PathVariable Long id, @RequestBody Fuwugoumai fuwugoumai) {
        try {
            Fuwugoumai updatedFuwugoumai = fuwugoumaiService.updateFuwugoumai(id, fuwugoumai);
            return new ResponseEntity<>(updatedFuwugoumai, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuwugoumai(@PathVariable Long id) {
        try {
            fuwugoumaiService.deleteFuwugoumai(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/laoren/{laorenUuid}")
    public ResponseEntity<List<Fuwugoumai>> getFuwugoumaiByLaoren(@PathVariable String laorenUuid) {
        List<Fuwugoumai> fuwugoumaiList = fuwugoumaiService.getByLaorenUuid(laorenUuid);
        return new ResponseEntity<>(fuwugoumaiList, HttpStatus.OK);
    }
    
    @GetMapping("/status/{goumaiYesno}")
    public ResponseEntity<List<Fuwugoumai>> getFuwugoumaiByStatus(@PathVariable String goumaiYesno) {
        List<Fuwugoumai> fuwugoumaiList = fuwugoumaiService.getByGoumaiYesno(goumaiYesno);
        return new ResponseEntity<>(fuwugoumaiList, HttpStatus.OK);
    }
}