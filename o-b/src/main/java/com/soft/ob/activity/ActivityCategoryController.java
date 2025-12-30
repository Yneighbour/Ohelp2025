package com.soft.ob.activity;

import com.soft.ob.entity.Huodongfenlei;
import com.soft.ob.activity.service.HuodongfenleiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/huodongfenlei")
@CrossOrigin(origins = "*")
public class ActivityCategoryController {
    
    @Autowired
    private HuodongfenleiService huodongfenleiService;
    
    @GetMapping
    public ResponseEntity<List<Huodongfenlei>> getAllHuodongfenlei() {
        List<Huodongfenlei> huodongfenleiList = huodongfenleiService.getAllHuodongfenlei();
        return new ResponseEntity<>(huodongfenleiList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Huodongfenlei> getHuodongfenleiById(@PathVariable Long id) {
        Optional<Huodongfenlei> huodongfenlei = huodongfenleiService.getHuodongfenleiById(id);
        return huodongfenlei.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Huodongfenlei> createHuodongfenlei(@RequestBody Huodongfenlei huodongfenlei) {
        Huodongfenlei savedHuodongfenlei = huodongfenleiService.saveHuodongfenlei(huodongfenlei);
        return new ResponseEntity<>(savedHuodongfenlei, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Huodongfenlei> updateHuodongfenlei(@PathVariable Long id, @RequestBody Huodongfenlei huodongfenlei) {
        try {
            Huodongfenlei updatedHuodongfenlei = huodongfenleiService.updateHuodongfenlei(id, huodongfenlei);
            return new ResponseEntity<>(updatedHuodongfenlei, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHuodongfenlei(@PathVariable Long id) {
        try {
            huodongfenleiService.deleteHuodongfenlei(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
