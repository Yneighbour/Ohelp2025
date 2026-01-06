package com.soft.ob.elder;

import com.soft.ob.entity.Elderly;
import com.soft.ob.elder.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/elderly")
@CrossOrigin(origins = "*")
public class ElderProfileController {
    
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
    
    // 添加注册接口
    @PostMapping("/register")
    public ResponseEntity<Elderly> registerElderly(@RequestBody Elderly elderly) {
        try {
            Elderly registeredElderly = elderlyService.registerElderly(elderly);
            return new ResponseEntity<>(registeredElderly, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    // 添加登录接口
    @PostMapping("/login")
    public ResponseEntity<Elderly> loginElderly(@RequestBody Elderly loginRequest) {
        try {
            Elderly elderly = elderlyService.loginElderly(loginRequest.getUsername(), loginRequest.getPassword());
            return new ResponseEntity<>(elderly, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    // 添加修改密码接口
    @PutMapping("/{id}/password")
    public ResponseEntity<Boolean> changeElderlyPassword(@PathVariable Long id, @RequestBody ElderlyPasswordChangeRequest request) {
        try {
            boolean result = elderlyService.changeElderlyPassword(id, request.getOldPassword(), request.getNewPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    
    // 添加更新个人信息接口
    @PutMapping("/{id}/profile")
    public ResponseEntity<Elderly> updateElderlyProfile(@PathVariable Long id, @RequestBody Elderly elderly) {
        try {
            Elderly updatedElderly = elderlyService.updateElderlyInfo(id, elderly);
            return new ResponseEntity<>(updatedElderly, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    public static class ElderlyPasswordChangeRequest {
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