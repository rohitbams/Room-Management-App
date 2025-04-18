package com.stacs.cs5031.p3.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.stacs.cs5031.p3.server.model.Admin;

@DataJpaTest
public class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;

    @Test
    void testSave() {
        Admin john = new Admin("John", "greatestJohn", "john123");
        adminRepository.save(john);
        int savedId = john.getId();

        Admin result = adminRepository.findById(savedId).orElseThrow();
        assertEquals(savedId, result.getId());
        assertEquals("John", result.getName());
        assertEquals("greatestJohn", result.getUsername());
        assertEquals("john123", result.getPassword());
    }

    @Test
    void testUpdate() {
        Admin john = new Admin("John", "greatestJohn", "john123");
        adminRepository.save(john);
        john.setName("Johnny"); // update name
        adminRepository.save(john); // update name
        int savedId = john.getId();
        
        Admin result = adminRepository.findById(savedId).orElseThrow();
        assertEquals(savedId, result.getId());
        assertEquals("Johnny", result.getName());
        assertEquals("greatestJohn", result.getUsername());
        assertEquals("john123", result.getPassword());
    }
    
    @Test
    void testFindByUsername() {
        Admin john = new Admin("John", "greatestJohn", "john123");
        adminRepository.save(john);
        
        Optional<Admin> result = adminRepository.findByUsername("greatestJohn");
        
        assertTrue(result.isPresent());
        
        Admin foundAdmin = result.get();
        assertEquals(john.getId(), foundAdmin.getId());
        assertEquals("John", foundAdmin.getName());
        assertEquals("greatestJohn", foundAdmin.getUsername());
        assertEquals("john123", foundAdmin.getPassword());
    }
    
    @Test
    void testFindAll() {
        Admin john = new Admin("John", "greatestJohn", "john123");
        Admin tom = new Admin("Tom", "tom", "tom123");
        Admin sam = new Admin("Sam", "samthegreat", "samisgreat");

        adminRepository.saveAll(List.of(john, tom, sam));
        List<Admin> result = adminRepository.findAll();

        assertEquals(3, result.size());
    }

    @Test
    void testDeleteById() {
        Admin john = new Admin("John", "greatestJohn", "john123");
        adminRepository.save(john);
        int id = john.getId();
        adminRepository.deleteById(id);
        Optional<Admin> result = adminRepository.findById(id);
        assertTrue(result.isEmpty());
    }
}
