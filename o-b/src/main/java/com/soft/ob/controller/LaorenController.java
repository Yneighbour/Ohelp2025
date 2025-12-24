package com.soft.ob.controller;

import com.soft.ob.entity.Laoren;
import com.soft.ob.service.LaorenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/laoren")
@CrossOrigin(origins = "*")
public class LaorenController {
    
    @Autowired
    private LaorenService laorenService;
    
    @GetMapping
    public ResponseEntity<List<Laoren>> getAllLaoren() {
        List<Laoren> laorenList = laorenService.getAllLaoren();
        return new ResponseEntity<>(laorenList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Laoren> getLaorenById(@PathVariable Long id) {
        Optional<Laoren> laoren = laorenService.getLaorenById(id);
        return laoren.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/register")
    public ResponseEntity<Laoren> registerLaoren(@RequestBody Laoren laoren) {
        try {
            Laoren registeredLaoren = laorenService.registerLaoren(laoren);
            return new ResponseEntity<>(registeredLaoren, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Laoren> loginLaoren(@RequestBody Laoren loginRequest) {
        try {
            Laoren laoren = laorenService.loginLaoren(loginRequest.getUsername(), loginRequest.getPassword());
            return new ResponseEntity<>(laoren, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Laoren> updateLaoren(@PathVariable Long id, @RequestBody Laoren laoren) {
        try {
            Laoren updatedLaoren = laorenService.updateLaoren(id, laoren);
            return new ResponseEntity<>(updatedLaoren, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaoren(@PathVariable Long id) {
        try {
            laorenService.deleteLaoren(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/password")
    public ResponseEntity<Boolean> changeLaorenPassword(@PathVariable Long id, @RequestBody LaorenPasswordChangeRequest request) {
        try {
            boolean result = laorenService.changeLaorenPassword(id, request.getOldPassword(), request.getNewPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}/profile")
    public ResponseEntity<Laoren> updateLaorenProfile(@PathVariable Long id, @RequestBody Laoren laoren) {
        try {
            Laoren updatedLaoren = laorenService.updateLaorenInfo(id, laoren);
            return new ResponseEntity<>(updatedLaoren, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Inner class for password change request
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