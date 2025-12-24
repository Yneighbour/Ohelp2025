package com.soft.ob.controller;

import com.soft.ob.entity.Meirijiankang;
import com.soft.ob.service.MeirijiankangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meirijiankang")
@CrossOrigin(origins = "*")
public class MeirijiankangController {
    
    @Autowired
    private MeirijiankangService meirijiankangService;
    
    @GetMapping
    public ResponseEntity<List<Meirijiankang>> getAllMeirijiankang() {
        List<Meirijiankang> meirijiankangList = meirijiankangService.getAllMeirijiankang();
        return new ResponseEntity<>(meirijiankangList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Meirijiankang> getMeirijiankangById(@PathVariable Long id) {
        Optional<Meirijiankang> meirijiankang = meirijiankangService.getMeirijiankangById(id);
        return meirijiankang.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Meirijiankang> createMeirijiankang(@RequestBody Meirijiankang meirijiankang) {
        Meirijiankang savedMeirijiankang = meirijiankangService.saveMeirijiankang(meirijiankang);
        return new ResponseEntity<>(savedMeirijiankang, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Meirijiankang> updateMeirijiankang(@PathVariable Long id, @RequestBody Meirijiankang meirijiankang) {
        try {
            Meirijiankang updatedMeirijiankang = meirijiankangService.updateMeirijiankang(id, meirijiankang);
            return new ResponseEntity<>(updatedMeirijiankang, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeirijiankang(@PathVariable Long id) {
        try {
            meirijiankangService.deleteMeirijiankang(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}