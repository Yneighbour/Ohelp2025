package com.soft.ob.controller;

import com.soft.ob.entity.Laogong;
import com.soft.ob.service.LaogongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
//laogong,是老工，工作的工，老工管理(`LaogongController`)：管理工作人员信息，可能会和照料产生冲突，我猜应该不会的
@RestController
@RequestMapping("/api/laogong")
@CrossOrigin(origins = "*")
public class LaogongController {
    
    @Autowired
    private LaogongService laogongService;
    
    @GetMapping
    public ResponseEntity<List<Laogong>> getAllLaogong() {
        List<Laogong> laogongList = laogongService.getAllLaogong();
        return new ResponseEntity<>(laogongList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Laogong> getLaogongById(@PathVariable Long id) {
        Optional<Laogong> laogong = laogongService.getLaogongById(id);
        return laogong.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Laogong> createLaogong(@RequestBody Laogong laogong) {
        Laogong savedLaogong = laogongService.saveLaogong(laogong);
        return new ResponseEntity<>(savedLaogong, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Laogong> updateLaogong(@PathVariable Long id, @RequestBody Laogong laogong) {
        try {
            Laogong updatedLaogong = laogongService.updateLaogong(id, laogong);
            return new ResponseEntity<>(updatedLaogong, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaogong(@PathVariable Long id) {
        try {
            laogongService.deleteLaogong(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Laogong> loginLaogong(@RequestBody Laogong loginRequest) {
        try {
            Laogong laogong = laogongService.loginLaogong(loginRequest.getUsername(), loginRequest.getPassword());
            return new ResponseEntity<>(laogong, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PutMapping("/{id}/password")
    public ResponseEntity<Boolean> changeLaogongPassword(@PathVariable Long id, @RequestBody LaogongPasswordChangeRequest request) {
        try {
            boolean result = laogongService.changeLaogongPassword(id, request.getOldPassword(), request.getNewPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    
    // Inner class for password change request
    public static class LaogongPasswordChangeRequest {
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