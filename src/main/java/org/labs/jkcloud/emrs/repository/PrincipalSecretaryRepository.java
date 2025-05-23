package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.PrincipalSecretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrincipalSecretaryRepository extends JpaRepository<PrincipalSecretary, Long> {

    List<PrincipalSecretary> findByType(String type);
}
