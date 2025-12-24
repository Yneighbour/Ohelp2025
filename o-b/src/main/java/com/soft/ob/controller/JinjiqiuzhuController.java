package com.soft.ob.controller;

import com.soft.ob.entity.Jinjiqiuzhu;
import com.soft.ob.service.JinjiqiuzhuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jinjiqiuzhu")
@CrossOrigin(origins = "*")
public class JinjiqiuzhuController {
    
    @Autowired
    private JinjiqiuzhuService jinjiqiuzhuService;
    
    @GetMapping
    public ResponseEntity<List<Jinjiqiuzhu>> getAllJinjiqiuzhu() {
        List<Jinjiqiuzhu> jinjiqiuzhuList = jinjiqiuzhuService.getAllJinjiqiuzhu();
        return new ResponseEntity<>(jinjiqiuzhuList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Jinjiqiuzhu> getJinjiqiuzhuById(@PathVariable Long id) {
        Optional<Jinjiqiuzhu> jinjiqiuzhu = jinjiqiuzhuService.getJinjiqiuzhuById(id);
        return jinjiqiuzhu.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Jinjiqiuzhu> createJinjiqiuzhu(@RequestBody Jinjiqiuzhu jinjiqiuzhu) {
        Jinjiqiuzhu savedJinjiqiuzhu = jinjiqiuzhuService.saveJinjiqiuzhu(jinjiqiuzhu);
        return new ResponseEntity<>(savedJinjiqiuzhu, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Jinjiqiuzhu> updateJinjiqiuzhu(@PathVariable Long id, @RequestBody Jinjiqiuzhu jinjiqiuzhu) {
        try {
            Jinjiqiuzhu updatedJinjiqiuzhu = jinjiqiuzhuService.updateJinjiqiuzhu(id, jinjiqiuzhu);
            return new ResponseEntity<>(updatedJinjiqiuzhu, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJinjiqiuzhu(@PathVariable Long id) {
        try {
            jinjiqiuzhuService.deleteJinjiqiuzhu(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/laoren/{laorenUuid}")
    public ResponseEntity<List<Jinjiqiuzhu>> getJinjiqiuzhuByLaoren(@PathVariable String laorenUuid) {
        List<Jinjiqiuzhu> jinjiqiuzhuList = jinjiqiuzhuService.getByLaorenUuid(laorenUuid);
        return new ResponseEntity<>(jinjiqiuzhuList, HttpStatus.OK);
    }
}