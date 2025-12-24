package com.soft.ob.controller;

import com.soft.ob.entity.Fuwuxiangmu;
import com.soft.ob.service.FuwuxiangmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fuwuxiangmu")
@CrossOrigin(origins = "*")
public class FuwuxiangmuController {
    
    @Autowired
    private FuwuxiangmuService fuwuxiangmuService;
    
    @GetMapping
    public ResponseEntity<List<Fuwuxiangmu>> getAllFuwuxiangmu() {
        List<Fuwuxiangmu> fuwuxiangmuList = fuwuxiangmuService.getAllFuwuxiangmu();
        return new ResponseEntity<>(fuwuxiangmuList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Fuwuxiangmu> getFuwuxiangmuById(@PathVariable Long id) {
        Optional<Fuwuxiangmu> fuwuxiangmu = fuwuxiangmuService.getFuwuxiangmuById(id);
        return fuwuxiangmu.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Fuwuxiangmu> createFuwuxiangmu(@RequestBody Fuwuxiangmu fuwuxiangmu) {
        Fuwuxiangmu savedFuwuxiangmu = fuwuxiangmuService.saveFuwuxiangmu(fuwuxiangmu);
        return new ResponseEntity<>(savedFuwuxiangmu, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Fuwuxiangmu> updateFuwuxiangmu(@PathVariable Long id, @RequestBody Fuwuxiangmu fuwuxiangmu) {
        try {
            Fuwuxiangmu updatedFuwuxiangmu = fuwuxiangmuService.updateFuwuxiangmu(id, fuwuxiangmu);
            return new ResponseEntity<>(updatedFuwuxiangmu, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuwuxiangmu(@PathVariable Long id) {
        try {
            fuwuxiangmuService.deleteFuwuxiangmu(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/type/{fuwuleixingId}")
    public ResponseEntity<List<Fuwuxiangmu>> getFuwuxiangmuByType(@PathVariable Long fuwuleixingId) {
        List<Fuwuxiangmu> fuwuxiangmuList = fuwuxiangmuService.getByFuwuleixingId(fuwuleixingId);
        return new ResponseEntity<>(fuwuxiangmuList, HttpStatus.OK);
    }
    
    @GetMapping("/shangxia/{shangxia}")
    public ResponseEntity<List<Fuwuxiangmu>> getFuwuxiangmuByShangxia(@PathVariable Integer shangxia) {
        List<Fuwuxiangmu> fuwuxiangmuList = fuwuxiangmuService.getByShangxia(shangxia);
        return new ResponseEntity<>(fuwuxiangmuList, HttpStatus.OK);
    }
}