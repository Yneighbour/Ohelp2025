package com.soft.ob.controller;

import com.soft.ob.entity.Pingjia;
import com.soft.ob.service.PingjiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pingjia")
@CrossOrigin(origins = "*")
public class PingjiaController {
    
    @Autowired
    private PingjiaService pingjiaService;
    
    @GetMapping
    public ResponseEntity<List<Pingjia>> getAllPingjia() {
        List<Pingjia> pingjiaList = pingjiaService.getAllPingjia();
        return new ResponseEntity<>(pingjiaList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pingjia> getPingjiaById(@PathVariable Long id) {
        Optional<Pingjia> pingjia = pingjiaService.getPingjiaById(id);
        return pingjia.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Pingjia> createPingjia(@RequestBody Pingjia pingjia) {
        Pingjia savedPingjia = pingjiaService.savePingjia(pingjia);
        return new ResponseEntity<>(savedPingjia, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Pingjia> updatePingjia(@PathVariable Long id, @RequestBody Pingjia pingjia) {
        try {
            Pingjia updatedPingjia = pingjiaService.updatePingjia(id, pingjia);
            return new ResponseEntity<>(updatedPingjia, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePingjia(@PathVariable Long id) {
        try {
            pingjiaService.deletePingjia(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/laoren/{laorenId}")
    public ResponseEntity<List<Pingjia>> getPingjiaByLaorenId(@PathVariable Long laorenId) {
        List<Pingjia> pingjiaList = pingjiaService.getByLaorenId(laorenId);
        return new ResponseEntity<>(pingjiaList, HttpStatus.OK);
    }
    
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<Pingjia>> getPingjiaByStaffId(@PathVariable Long staffId) {
        List<Pingjia> pingjiaList = pingjiaService.getByStaffId(staffId);
        return new ResponseEntity<>(pingjiaList, HttpStatus.OK);
    }
    
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<Pingjia>> getPingjiaByServiceId(@PathVariable Long serviceId) {
        List<Pingjia> pingjiaList = pingjiaService.getByServiceId(serviceId);
        return new ResponseEntity<>(pingjiaList, HttpStatus.OK);
    }
    
    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Pingjia>> getPingjiaByRating(@PathVariable Integer rating) {
        List<Pingjia> pingjiaList = pingjiaService.getByRating(rating);
        return new ResponseEntity<>(pingjiaList, HttpStatus.OK);
    }
    
    @GetMapping("/type/{serviceType}")
    public ResponseEntity<List<Pingjia>> getPingjiaByServiceType(@PathVariable String serviceType) {
        List<Pingjia> pingjiaList = pingjiaService.getByServiceType(serviceType);
        return new ResponseEntity<>(pingjiaList, HttpStatus.OK);
    }
}