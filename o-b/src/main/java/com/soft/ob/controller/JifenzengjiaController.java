package com.soft.ob.controller;

import com.soft.ob.entity.Jifenzengjia;
import com.soft.ob.service.JifenzengjiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jifenzengjia")
@CrossOrigin(origins = "*")
public class JifenzengjiaController {
    
    @Autowired
    private JifenzengjiaService jifenzengjiaService;
    
    @GetMapping
    public ResponseEntity<List<Jifenzengjia>> getAllJifenzengjia() {
        List<Jifenzengjia> jifenzengjiaList = jifenzengjiaService.getAllJifenzengjia();
        return new ResponseEntity<>(jifenzengjiaList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Jifenzengjia> getJifenzengjiaById(@PathVariable Long id) {
        Optional<Jifenzengjia> jifenzengjia = jifenzengjiaService.getJifenzengjiaById(id);
        return jifenzengjia.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Jifenzengjia> createJifenzengjia(@RequestBody Jifenzengjia jifenzengjia) {
        Jifenzengjia savedJifenzengjia = jifenzengjiaService.saveJifenzengjia(jifenzengjia);
        return new ResponseEntity<>(savedJifenzengjia, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Jifenzengjia> updateJifenzengjia(@PathVariable Long id, @RequestBody Jifenzengjia jifenzengjia) {
        try {
            Jifenzengjia updatedJifenzengjia = jifenzengjiaService.updateJifenzengjia(id, jifenzengjia);
            return new ResponseEntity<>(updatedJifenzengjia, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJifenzengjia(@PathVariable Long id) {
        try {
            jifenzengjiaService.deleteJifenzengjia(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/laoren/{laorenUuid}")
    public ResponseEntity<List<Jifenzengjia>> getJifenzengjiaByLaoren(@PathVariable String laorenUuid) {
        List<Jifenzengjia> jifenzengjiaList = jifenzengjiaService.getByLaorenUuid(laorenUuid);
        return new ResponseEntity<>(jifenzengjiaList, HttpStatus.OK);
    }
}