package com.soft.ob.health;

import com.soft.ob.entity.Jiwangbingshi;
import com.soft.ob.health.service.JiwangbingshiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jiwangbingshi")
@CrossOrigin(origins = "*")
public class MedicalHistoryController {
    
    @Autowired
    private JiwangbingshiService jiwangbingshiService;
    
    @GetMapping
    public ResponseEntity<List<Jiwangbingshi>> getAllJiwangbingshi() {
        List<Jiwangbingshi> jiwangbingshiList = jiwangbingshiService.getAllJiwangbingshi();
        return new ResponseEntity<>(jiwangbingshiList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Jiwangbingshi> getJiwangbingshiById(@PathVariable Long id) {
        Optional<Jiwangbingshi> jiwangbingshi = jiwangbingshiService.getJiwangbingshiById(id);
        return jiwangbingshi.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Jiwangbingshi> createJiwangbingshi(@RequestBody Jiwangbingshi jiwangbingshi) {
        Jiwangbingshi savedJiwangbingshi = jiwangbingshiService.saveJiwangbingshi(jiwangbingshi);
        return new ResponseEntity<>(savedJiwangbingshi, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Jiwangbingshi> updateJiwangbingshi(@PathVariable Long id, @RequestBody Jiwangbingshi jiwangbingshi) {
        try {
            Jiwangbingshi updatedJiwangbingshi = jiwangbingshiService.updateJiwangbingshi(id, jiwangbingshi);
            return new ResponseEntity<>(updatedJiwangbingshi, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJiwangbingshi(@PathVariable Long id) {
        try {
            jiwangbingshiService.deleteJiwangbingshi(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/laoren/{laorenUuid}")
    public ResponseEntity<List<Jiwangbingshi>> getJiwangbingshiByLaoren(@PathVariable String laorenUuid) {
        List<Jiwangbingshi> jiwangbingshiList = jiwangbingshiService.getByLaorenUuid(laorenUuid);
        return new ResponseEntity<>(jiwangbingshiList, HttpStatus.OK);
    }
}
