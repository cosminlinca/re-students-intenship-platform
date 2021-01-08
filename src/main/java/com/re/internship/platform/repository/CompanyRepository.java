package com.re.internship.platform.repository;

import com.re.internship.platform.domain.Company;
import com.re.internship.platform.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    @Query("select c from Company c where c.user = ?1")
    Optional<Company> findByUserId(User user);
}
