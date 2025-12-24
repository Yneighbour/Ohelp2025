package com.soft.ob.controller;

import com.soft.ob.entity.Huodongxinxi;
import com.soft.ob.service.HuodongxinxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/huodongxinxi")
@CrossOrigin(origins = "*")
public class HuodongxinxiController {
    
    @Autowired
    private HuodongxinxiService huodongxinxiService;
    
    @GetMapping
    public ResponseEntity<List<Huodongxinxi>> getAllHuodongxinxi() {
        List<Huodongxinxi> huodongxinxiList = huodongxinxiService.getAllHuodongxinxi();
        return new ResponseEntity<>(huodongxinxiList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Huodongxinxi> getHuodongxinxiById(@PathVariable Long id) {
        Optional<Huodongxinxi> huodongxinxi = huodongxinxiService.getHuodongxinxiById(id);
        return huodongxinxi.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Huodongxinxi> createHuodongxinxi(@RequestBody Huodongxinxi huodongxinxi) {
        Huodongxinxi savedHuodongxinxi = huodongxinxiService.saveHuodongxinxi(huodongxinxi);
        return new ResponseEntity<>(savedHuodongxinxi, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Huodongxinxi> updateHuodongxinxi(@PathVariable Long id, @RequestBody Huodongxinxi huodongxinxi) {
        try {
            Huodongxinxi updatedHuodongxinxi = huodongxinxiService.updateHuodongxinxi(id, huodongxinxi);
            return new ResponseEntity<>(updatedHuodongxinxi, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHuodongxinxi(@PathVariable Long id) {
        try {
            huodongxinxiService.deleteHuodongxinxi(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/category/{huodongfenleiId}")
    public ResponseEntity<List<Huodongxinxi>> getHuodongxinxiByCategory(@PathVariable Long huodongfenleiId) {
        List<Huodongxinxi> huodongxinxiList = huodongxinxiService.getByHuodongfenleiId(huodongfenleiId);
        return new ResponseEntity<>(huodongxinxiList, HttpStatus.OK);
    }
    
    @GetMapping("/status/{huodongYesno}")
    public ResponseEntity<List<Huodongxinxi>> getHuodongxinxiByStatus(@PathVariable String huodongYesno) {
        List<Huodongxinxi> huodongxinxiList = huodongxinxiService.getByHuodongYesno(huodongYesno);
        return new ResponseEntity<>(huodongxinxiList, HttpStatus.OK);
    }
}