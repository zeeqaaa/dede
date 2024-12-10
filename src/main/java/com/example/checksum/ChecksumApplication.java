package com.example.checksum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.security.MessageDigest;
import java.util.List;

@SpringBootApplication
public class ChecksumApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChecksumApplication.class, args);
    }
}

// Controller
@RestController
@RequestMapping("/api")
class ChecksumController {

    @Autowired
    private ChecksumService service;

    @GetMapping("/checksum")
    public ChecksumEntry calculateChecksum(@RequestParam String input,
                                           @RequestParam(defaultValue = "SHA-256") String algorithm) throws Exception {
        String checksum = ChecksumUtil.calculateChecksum(input, algorithm);
        return service.saveEntry(input, checksum, algorithm);
    }

    @GetMapping("/entries")
    public List<ChecksumEntry> getAllEntries() {
        return service.getAllEntries();
    }
}

// Service
@Service
class ChecksumService {

    private final ChecksumRepository repository;

    @Autowired
    public ChecksumService(ChecksumRepository repository) {
        this.repository = repository;
    }

    public ChecksumEntry saveEntry(String inputString, String checksum, String algorithm) {
        ChecksumEntry entry = new ChecksumEntry();
        entry.setInputString(inputString);
        entry.setChecksum(checksum);
        entry.setAlgorithm(algorithm);
        return repository.save(entry);
    }

    public List<ChecksumEntry> getAllEntries() {
        return repository.findAll();
    }
}

// Repository
interface ChecksumRepository extends JpaRepository<ChecksumEntry, Long> {}

// Entity
@Entity
class ChecksumEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputString;
    private String checksum;
    private String algorithm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}

// Utility Class
class ChecksumUtil {

    public static String calculateChecksum(String input, String algorithm) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = messageDigest.digest(input.getBytes());
        StringBuilder hashString = new StringBuilder();
        for (byte b : hashBytes) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }
}