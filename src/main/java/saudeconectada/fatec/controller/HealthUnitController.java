package saudeconectada.fatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saudeconectada.fatec.domain.model.HealthUnit;
import saudeconectada.fatec.service.HealthUnitService;

import java.util.List;

@RestController
@RequestMapping("api/healthunit")
public class HealthUnitController {

    @Autowired
    private HealthUnitService healthUnitService;

    @GetMapping
    public ResponseEntity<List<HealthUnit>> getAllHealthUnits() {
        try {
            List<HealthUnit> healthUnits = healthUnitService.getAllHealthUnits();
            if (healthUnits.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(healthUnits);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<HealthUnit> registerHealthUnit(@RequestBody HealthUnit healthUnit) {
        try {
            HealthUnit newHealthUnit = healthUnitService.postHealthUnit(healthUnit);
            return ResponseEntity.ok(newHealthUnit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
