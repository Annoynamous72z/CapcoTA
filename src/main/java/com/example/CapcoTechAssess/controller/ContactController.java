package com.example.CapcoTechAssess.controller;

import com.example.CapcoTechAssess.model.Contact;
import com.example.CapcoTechAssess.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capcoapi/assessment")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/contacts")
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @PostMapping("/contacts")
    public Contact createEmployee(@Valid @RequestBody Contact contact) {
        return contactRepository.save(contact);
    }
}
