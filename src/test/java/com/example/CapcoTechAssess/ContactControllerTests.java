package com.example.CapcoTechAssess;


import com.example.CapcoTechAssess.controller.ContactController;
import com.example.CapcoTechAssess.model.Contact;
import com.example.CapcoTechAssess.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactController.class)
public class ContactControllerTests {
    @MockBean
    private ContactRepository contactRepository;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final long SOME_ID = 1L;
    private final long SOME_OTHER_ID = 2L;
    private final String SOME_FIRST_NAME = "Ryan";
    private final String SOME_OTHER_FIRST_NAME = "Brian";
    private final String SOME_LAST_NAME = "Shi";
    private final String SOME_PHONE_NUMBER = "1234567890";
    private final String  SOME_EMAIL = "test@test.com";
    private final String SOME_ADDRESS = "123 Nowhere Drive";
    private final Contact SOME_CONTACT = new Contact(SOME_ID, SOME_FIRST_NAME, SOME_LAST_NAME, SOME_PHONE_NUMBER, SOME_EMAIL, SOME_ADDRESS);
    private final Contact SOME_EDITED_CONTACT = new Contact(SOME_ID, SOME_OTHER_FIRST_NAME, SOME_LAST_NAME, SOME_PHONE_NUMBER, SOME_EMAIL, SOME_ADDRESS);
    private final Contact SOME_OTHER_CONTACT = new Contact(SOME_OTHER_ID, SOME_OTHER_FIRST_NAME, SOME_LAST_NAME, SOME_PHONE_NUMBER, SOME_EMAIL, SOME_ADDRESS);

    @Test
    void getAllContactSuccess() throws Exception {
        List<Contact> contacts = new ArrayList<>(Arrays.asList(SOME_CONTACT, SOME_OTHER_CONTACT));
        when(contactRepository.findAll()).thenReturn(contacts);

        mockMvc.perform(get("/capcoapi/assessment/contacts", SOME_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(contacts.size()))
                .andDo(print());

    }

    @Test
    void getSingleContactSuccess() throws Exception {
        when(contactRepository.findById(SOME_ID)).thenReturn(Optional.of(SOME_CONTACT));

        mockMvc.perform(get("/capcoapi/assessment/contacts/{id}", SOME_ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SOME_ID))
                .andExpect(jsonPath("$.firstName").value(SOME_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(SOME_LAST_NAME))
                .andExpect(jsonPath("$.phoneNumber").value(SOME_PHONE_NUMBER))
                .andExpect(jsonPath("$.email").value(SOME_EMAIL))
                .andExpect(jsonPath("$.address").value(SOME_ADDRESS))
                .andDo(print());
    }
    @Test
    void getSingleContactNotFound() throws Exception {
        when(contactRepository.findById(SOME_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/capcoapi/assessment/contacts/{id}", SOME_ID)).andExpect(status().isNotFound());

    }
    @Test
    void createContactSuccess() throws Exception {
        when(contactRepository.save(any(Contact.class))).thenReturn(SOME_CONTACT);
        mockMvc.perform(post("/capcoapi/assessment/contacts").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SOME_CONTACT)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SOME_ID))
                .andExpect(jsonPath("$.firstName").value(SOME_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(SOME_LAST_NAME))
                .andExpect(jsonPath("$.phoneNumber").value(SOME_PHONE_NUMBER))
                .andExpect(jsonPath("$.email").value(SOME_EMAIL))
                .andExpect(jsonPath("$.address").value(SOME_ADDRESS))
                .andDo(print());

    }
    @Test
    void updateContactSuccess() throws Exception {
        when(contactRepository.findById(SOME_ID)).thenReturn(Optional.of(SOME_CONTACT));
        when(contactRepository.save(any(Contact.class))).thenReturn(SOME_EDITED_CONTACT);
        mockMvc.perform(put("/capcoapi/assessment/contacts/{id}", SOME_ID).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SOME_EDITED_CONTACT)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SOME_ID))
                .andExpect(jsonPath("$.firstName").value(SOME_OTHER_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(SOME_LAST_NAME))
                .andExpect(jsonPath("$.phoneNumber").value(SOME_PHONE_NUMBER))
                .andExpect(jsonPath("$.email").value(SOME_EMAIL))
                .andExpect(jsonPath("$.address").value(SOME_ADDRESS))
                .andDo(print());

    }

    @Test
    void updateContactNotFound() throws Exception {
        when(contactRepository.findById(SOME_ID)).thenReturn(Optional.empty());

        mockMvc.perform(put("/capcoapi/assessment/contacts/{id}", SOME_ID).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SOME_EDITED_CONTACT))).andExpect(status().isNotFound());

    }

    @Test
    void deleteContactSuccess() throws Exception {
        when(contactRepository.findById(SOME_ID)).thenReturn(Optional.of(SOME_CONTACT));
        mockMvc.perform(delete("/capcoapi/assessment/contacts/{id}", SOME_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SOME_ID))
                .andExpect(jsonPath("$.firstName").value(SOME_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(SOME_LAST_NAME))
                .andExpect(jsonPath("$.phoneNumber").value(SOME_PHONE_NUMBER))
                .andExpect(jsonPath("$.email").value(SOME_EMAIL))
                .andExpect(jsonPath("$.address").value(SOME_ADDRESS))
                .andDo(print());

    }

    @Test
    void deleteAllContactSuccess() throws Exception {
        mockMvc.perform(delete("/capcoapi/assessment/contacts"))
                .andExpect(status().isOk())
                .andDo(print());

    }
}
