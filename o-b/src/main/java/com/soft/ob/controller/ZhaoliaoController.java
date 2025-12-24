package com.soft.ob.controller;

import com.soft.ob.entity.Zhaoliao;
import com.soft.ob.service.ZhaoliaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/zhaoliao")
@CrossOrigin(origins = "*")
public class ZhaoliaoController {
    
    @Autowired
    private ZhaoliaoService zhaoliaoService;
    
    @GetMapping
    public ResponseEntity<List<Zhaoliao>> getAllZhaoliao() {
        List<Zhaoliao> zhaoliaoList = zhaoliaoService.getAllZhaoliao();
        return new ResponseEntity<>(zhaoliaoList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Zhaoliao> getZhaoliaoById(@PathVariable Long id) {
        Optional<Zhaoliao> zhaoliao = zhaoliaoService.getZhaoliaoById(id);
        return zhaoliao.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Zhaoliao> createZhaoliao(@RequestBody Zhaoliao zhaoliao) {
        Zhaoliao savedZhaoliao = zhaoliaoService.saveZhaoliao(zhaoliao);
        return new ResponseEntity<>(savedZhaoliao, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Zhaoliao> updateZhaoliao(@PathVariable Long id, @RequestBody Zhaoliao zhaoliao) {
        try {
            Zhaoliao updatedZhaoliao = zhaoliaoService.updateZhaoliao(id, zhaoliao);
            return new ResponseEntity<>(updatedZhaoliao, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZhaoliao(@PathVariable Long id) {
        try {
            zhaoliaoService.deleteZhaoliao(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/laoren/{laorenId}")
    public ResponseEntity<List<Zhaoliao>> getZhaoliaoByLaorenId(@PathVariable Long laorenId) {
        List<Zhaoliao> zhaoliaoList = zhaoliaoService.getByLaorenId(laorenId);
        return new ResponseEntity<>(zhaoliaoList, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Zhaoliao>> getZhaoliaoByStatus(@PathVariable String status) {
        List<Zhaoliao> zhaoliaoList = zhaoliaoService.getByStatus(status);
        return new ResponseEntity<>(zhaoliaoList, HttpStatus.OK);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Zhaoliao>> getZhaoliaoByType(@PathVariable String type) {
        List<Zhaoliao> zhaoliaoList = zhaoliaoService.getByType(type);
        return new ResponseEntity<>(zhaoliaoList, HttpStatus.OK);
    }
    
    @GetMapping("/staff/{assignedStaff}")
    public ResponseEntity<List<Zhaoliao>> getZhaoliaoByAssignedStaff(@PathVariable String assignedStaff) {
        List<Zhaoliao> zhaoliaoList = zhaoliaoService.getByAssignedStaff(assignedStaff);
        return new ResponseEntity<>(zhaoliaoList, HttpStatus.OK);
    }
}