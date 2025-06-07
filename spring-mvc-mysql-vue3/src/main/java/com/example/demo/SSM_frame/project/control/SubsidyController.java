package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.Subsidy;
import com.example.demo.SSM_frame.project.service.SubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subsidy")
@CrossOrigin(origins = "http://localhost:5173")
public class SubsidyController {

    @Autowired
    private SubsidyService subsidyService;

    @GetMapping("/{subid}")
    public ResponseEntity<Subsidy> getSubsidyById(@PathVariable int subid) {
        Subsidy  subsidy = subsidyService.getSubsidyById(subid);
        return ResponseEntity.ok(subsidy);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Subsidy>> getAllSubsidies() {
        List<Subsidy> subsidies = subsidyService.getAllSubsidies();
        if (subsidies.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(subsidies);
    }
    @PostMapping("/add")
    public ResponseEntity<Subsidy> addSubsidy(@RequestBody Subsidy subsidy) {
        if (subsidy == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        subsidyService.insertsubsidy(subsidy);
        return ResponseEntity.status(HttpStatus.CREATED).body(subsidy);
    }
    @PostMapping("/addsign")
    public ResponseEntity<Subsidy> addSubsidysign(@RequestBody Subsidy subsidy) {
        if (subsidy == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        subsidyService.insertsubsidysign(subsidy);
        return ResponseEntity.status(HttpStatus.CREATED).body(subsidy);
    }
    @PutMapping("/update")
    public ResponseEntity<Subsidy> updateSubsidy(@RequestBody Subsidy subsidy) {
        if (subsidy==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        subsidyService.updateSubsidy(subsidy);
        return ResponseEntity.ok(subsidy);
        }
        @DeleteMapping("/delete/{subid}")
    public ResponseEntity<Void> deleteSubsidy(@PathVariable int subid) {
        if (subid==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
            subsidyService.deleteSubsidy(subid);
            return ResponseEntity.noContent().build();
        }
    @GetMapping("/getbyfarmer")
    public ResponseEntity<List<Subsidy>> getSubsidiesByFarmerId(@RequestParam String username) {
        List<Subsidy> subsidies = subsidyService.getSubsidiesByFarmerId(username);
        if (subsidies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(subsidies);
    }
    @GetMapping("/allsub")
    public ResponseEntity<List<Subsidy>> getAllSubsidiessign() {
        List<Subsidy> subsidies = subsidyService.getAllSubsidiessign();
        if (subsidies.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(subsidies);
    }
    @PostMapping("/approve")
    public ResponseEntity<String> approveUser(@RequestBody Map<String, Object> payload) {
        Number subIdNum = (Number) payload.get("subid");
        if (subIdNum == null) {
            return ResponseEntity.badRequest().body("subid 为空");
        }
        int subId = subIdNum.intValue();
        subsidyService.updateSubsidyStatus(subId, "已通过");
        return ResponseEntity.ok("审核通过");
    }
    @PostMapping("/reject")
    public ResponseEntity<String> rejectUser(@RequestBody Map<String, Object> payload) {
        Number subIdNum = (Number) payload.get("subid");
        if (subIdNum == null) {
            return ResponseEntity.badRequest().body("subid 为空");
        }
        int subId = subIdNum.intValue();
        subsidyService.updateSubsidyStatus(subId, "已拒绝");
        return ResponseEntity.ok("审核拒绝");
    }
}
