package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
