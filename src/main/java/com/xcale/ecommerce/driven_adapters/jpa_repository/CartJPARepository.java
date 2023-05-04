package com.xcale.ecommerce.driven_adapters.jpa_repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartJPARepository extends CrudRepository<CartJPA, String> {
}
