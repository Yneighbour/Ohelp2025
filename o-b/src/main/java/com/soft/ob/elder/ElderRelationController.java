package com.soft.ob.elder;

import com.soft.ob.entity.Qinshu;
import com.soft.ob.elder.service.QinshuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/qinshu")
@CrossOrigin(origins = "*")
public class ElderRelationController {
    
    @Autowired
    private QinshuService qinshuService;
    
    @GetMapping
    public ResponseEntity<List<Qinshu>> getAllQinshu() {
        List<Qinshu> qinshuList = qinshuService.getAllQinshu();
        return new ResponseEntity<>(qinshuList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Qinshu> getQinshuById(@PathVariable Long id) {
        Optional<Qinshu> qinshu = qinshuService.getQinshuById(id);
        return qinshu.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Qinshu> createQinshu(@RequestBody Qinshu qinshu) {
        Qinshu savedQinshu = qinshuService.saveQinshu(qinshu);
        return new ResponseEntity<>(savedQinshu, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Qinshu> updateQinshu(@PathVariable Long id, @RequestBody Qinshu qinshu) {
        try {
            Qinshu updatedQinshu = qinshuService.updateQinshu(id, qinshu);
            return new ResponseEntity<>(updatedQinshu, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQinshu(@PathVariable Long id) {
        try {
            qinshuService.deleteQinshu(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/laoren/{laorenUuid}")
    public ResponseEntity<List<Qinshu>> getQinshuByLaoren(@PathVariable String laorenUuid) {
        List<Qinshu> qinshuList = qinshuService.getByLaorenUuid(laorenUuid);
        return new ResponseEntity<>(qinshuList, HttpStatus.OK);
    }
}
