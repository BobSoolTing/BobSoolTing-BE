package bst.bobsoolting.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CheckController {

    @GetMapping("/")
    public ResponseEntity<String> rootCheck() {
        return ResponseEntity.ok("Server is running");
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Health check OK");
    }
}
