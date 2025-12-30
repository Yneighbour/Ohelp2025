package com.soft.ob.activity;

import com.soft.ob.entity.Tanwang;
import com.soft.ob.activity.service.TanwangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tanwang")
@CrossOrigin(origins = "*")
public class VisitController {
    
    @Autowired
    private TanwangService tanwangService;
    
    @GetMapping
    public ResponseEntity<List<Tanwang>> getAllTanwang() {
        List<Tanwang> tanwangList = tanwangService.getAllTanwang();
        return new ResponseEntity<>(tanwangList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tanwang> getTanwangById(@PathVariable Long id) {
        Optional<Tanwang> tanwang = tanwangService.getTanwangById(id);
        return tanwang.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Tanwang> createTanwang(@RequestBody Tanwang tanwang) {
        Tanwang savedTanwang = tanwangService.saveTanwang(tanwang);
        return new ResponseEntity<>(savedTanwang, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Tanwang> updateTanwang(@PathVariable Long id, @RequestBody Tanwang tanwang) {
        try {
            Tanwang updatedTanwang = tanwangService.updateTanwang(id, tanwang);
            return new ResponseEntity<>(updatedTanwang, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTanwang(@PathVariable Long id) {
        try {
            tanwangService.deleteTanwang(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/laoren/{laorenId}")
    public ResponseEntity<List<Tanwang>> getTanwangByLaorenId(@PathVariable Long laorenId) {
        List<Tanwang> tanwangList = tanwangService.getByLaorenId(laorenId);
        return new ResponseEntity<>(tanwangList, HttpStatus.OK);
    }
    
    @GetMapping("/visitor/{visitorName}")
    public ResponseEntity<List<Tanwang>> getTanwangByVisitorName(@PathVariable String visitorName) {
        List<Tanwang> tanwangList = tanwangService.getByVisitorName(visitorName);
        return new ResponseEntity<>(tanwangList, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Tanwang>> getTanwangByStatus(@PathVariable String status) {
        List<Tanwang> tanwangList = tanwangService.getByStatus(status);
        return new ResponseEntity<>(tanwangList, HttpStatus.OK);
    }
    
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<Tanwang>> getTanwangByStaffId(@PathVariable String staffId) {
        List<Tanwang> tanwangList = tanwangService.getByStaffId(staffId);
        return new ResponseEntity<>(tanwangList, HttpStatus.OK);
    }
    
    @GetMapping("/time-range")
    public ResponseEntity<List<Tanwang>> getTanwangByTimeRange(
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime) {
        List<Tanwang> tanwangList = tanwangService.getByVisitTimeBetween(startTime, endTime);
        return new ResponseEntity<>(tanwangList, HttpStatus.OK);
    }
}
