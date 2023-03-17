package com.example.CapcoTechAssess.repository;

import com.example.CapcoTechAssess.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
}
