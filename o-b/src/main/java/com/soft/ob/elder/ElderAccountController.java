package com.soft.ob.elder;

import com.soft.ob.entity.Elderly;
import com.soft.ob.elder.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/laoren")
@CrossOrigin(origins = "*")
public class ElderAccountController {
    
    @Autowired
    private ElderlyService elderlyService;
    
    @GetMapping
    public ResponseEntity<List<Elderly>> getAllLaoren() {
        List<Elderly> elderlyList = elderlyService.getAllElderly();
        return new ResponseEntity<>(elderlyList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Elderly> getLaorenById(@PathVariable Long id) {
        Optional<Elderly> elderly = elderlyService.getElderlyById(id);
        return elderly.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/register")
    public ResponseEntity<Elderly> registerLaoren(@RequestBody Elderly elderly) {
        try {
            Elderly registeredElderly = elderlyService.registerElderly(elderly);
            return new ResponseEntity<>(registeredElderly, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Elderly> loginLaoren(@RequestBody Elderly loginRequest) {
        try {
            Elderly elderly = elderlyService.loginElderly(loginRequest.getUsername(), loginRequest.getPassword());
            return new ResponseEntity<>(elderly, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Elderly> updateLaoren(@PathVariable Long id, @RequestBody Elderly elderly) {
        try {
            Elderly updatedElderly = elderlyService.updateElderly(id, elderly);
            return new ResponseEntity<>(updatedElderly, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaoren(@PathVariable Long id) {
        try {
            elderlyService.deleteElderly(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/password")
    public ResponseEntity<Boolean> changeLaorenPassword(@PathVariable Long id, @RequestBody LaorenPasswordChangeRequest request) {
        try {
            boolean result = elderlyService.changeElderlyPassword(id, request.getOldPassword(), request.getNewPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}/profile")
    public ResponseEntity<Elderly> updateLaorenProfile(@PathVariable Long id, @RequestBody Elderly elderly) {
        try {
            Elderly updatedElderly = elderlyService.updateElderlyInfo(id, elderly);
            return new ResponseEntity<>(updatedElderly, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    public static class LaorenPasswordChangeRequest {
        private String oldPassword;
        private String newPassword;
        
        public String getOldPassword() {
            return oldPassword;
        }
        
        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }
        
        public String getNewPassword() {
            return newPassword;
        }
        
        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}