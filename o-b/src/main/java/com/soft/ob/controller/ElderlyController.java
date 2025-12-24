package com.soft.ob.controller;

import com.soft.ob.entity.Elderly;
import com.soft.ob.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/elderly")
@CrossOrigin(origins = "*")
public class ElderlyController {
    
    @Autowired
    private ElderlyService elderlyService;
    
    @GetMapping
    public ResponseEntity<List<Elderly>> getAllElderly() {
        List<Elderly> elderlyList = elderlyService.getAllElderly();
        return new ResponseEntity<>(elderlyList, HttpStatus.OK);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Elderly>> getActiveElderly() {
        List<Elderly> elderlyList = elderlyService.getActiveElderly();
        return new ResponseEntity<>(elderlyList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Elderly> getElderlyById(@PathVariable Long id) {
        return elderlyService.getElderlyById(id)
            .map(elderly -> new ResponseEntity<>(elderly, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Elderly> createElderly(@RequestBody Elderly elderly) {
        Elderly savedElderly = elderlyService.saveElderly(elderly);
        return new ResponseEntity<>(savedElderly, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Elderly> updateElderly(@PathVariable Long id, @RequestBody Elderly elderlyDetails) {
        try {
            Elderly updatedElderly = elderlyService.updateElderly(id, elderlyDetails);
            return new ResponseEntity<>(updatedElderly, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElderly(@PathVariable Long id) {
        try {
            elderlyService.deleteElderly(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Elderly>> searchElderly(@RequestParam String name) {
        List<Elderly> elderlyList = elderlyService.searchElderlyByName(name);
        return new ResponseEntity<>(elderlyList, HttpStatus.OK);
    }
}