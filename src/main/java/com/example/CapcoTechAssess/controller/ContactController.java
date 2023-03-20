package com.example.CapcoTechAssess.controller;

import com.example.CapcoTechAssess.model.Contact;
import com.example.CapcoTechAssess.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Long contactID,
                                                 @Valid @RequestBody Contact newContactInfo) throws Exception {
        Contact contact = contactRepository.findById(contactID)
                .orElseThrow(() -> new Exception("Contact not found for this id :: " + contactID));

        contact.setFirstName(newContactInfo.getFirstName());
        contact.setLastName(newContactInfo.getLastName());
        contact.setAddress(newContactInfo.getAddress());
        contact.setPhoneNumber(newContactInfo.getPhoneNumber());
        contact.setEmail(newContactInfo.getEmail());

        final Contact updatedContact = contactRepository.save(contact);

        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable(value = "id") Long contactID) throws Exception {
        Contact contact = contactRepository.findById(contactID)
                .orElseThrow(() -> new Exception("Contact not found for this id :: " + contactID));

        contactRepository.delete(contact);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
}
