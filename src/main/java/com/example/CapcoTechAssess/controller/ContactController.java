package com.example.CapcoTechAssess.controller;

import com.example.CapcoTechAssess.model.Contact;
import com.example.CapcoTechAssess.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/capcoapi/assessment")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    /**
     * Gets all contacts in the database
     * @return List<Contacts></>
     */
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        try {
            return new ResponseEntity<>(contactRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets specified contact. If cannot be found returns Not Found, any other error Internal server error.
     * @param contactID id of contact to be found
     * @return Object of contact
     */
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable(value = "id") Long contactID){
        try {
            Optional<Contact> contact = contactRepository.findById(contactID);
            if (contact.isPresent()) {
                return new ResponseEntity<>(contact.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a contact
     * @param contact object of contact to be created
     * @return contect that is created
     */
    @PostMapping("/contacts")
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        try {
            Contact savedContact = contactRepository.save(contact);
            return new ResponseEntity<>(savedContact, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a contact
     * @param contactID id of contact to be updated
     * @param newContactInfo new info of contact
     * @return updated contact
     */
    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Long contactID,
                                                 @Valid @RequestBody Contact newContactInfo){
        try {
            Optional<Contact> contact = contactRepository.findById(contactID);

            if (contact.isPresent()) {
                Contact oldContact = contact.get();
                oldContact.setFirstName(newContactInfo.getFirstName());
                oldContact.setLastName(newContactInfo.getLastName());
                oldContact.setAddress(newContactInfo.getAddress());
                oldContact.setPhoneNumber(newContactInfo.getPhoneNumber());
                oldContact.setEmail(newContactInfo.getEmail());

                final Contact updatedContact = contactRepository.save(oldContact);
                return new ResponseEntity<>(updatedContact, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Deletes a specified contact
     * @param contactID Id of contact to be updated
     * @return contact that has been deleted
     */
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable(value = "id") Long contactID){
        try {
            Optional<Contact> contact = contactRepository.findById(contactID);
            if (contact.isPresent()) {
                contactRepository.delete(contact.get());
                return new ResponseEntity<>(contact.get(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Deletes All Contacts
     * @return Httpstatus.ok
     */
    @DeleteMapping("/contacts")
    public ResponseEntity<HttpStatus> deleteAll(){
        try {
            contactRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
