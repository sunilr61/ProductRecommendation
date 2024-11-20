package com.example.ecom.repositories;

import com.example.ecom.models.Product;
import com.example.ecom.models.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductGroupsRepository extends JpaRepository<ProductGroup, Integer> {
    public List<ProductGroup> findByProductsContaining(Product product);
}
