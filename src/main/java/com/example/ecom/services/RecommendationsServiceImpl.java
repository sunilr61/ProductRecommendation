package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.models.Product;
import com.example.ecom.models.ProductGroup;
import com.example.ecom.repositories.ProductGroupsRepository;
import com.example.ecom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class RecommendationsServiceImpl implements RecommendationsService{
    private ProductRepository productRepository;
    private ProductGroupsRepository productGroupsRepository;

    @Autowired
    public RecommendationsServiceImpl(ProductRepository productRepository,
                                      ProductGroupsRepository productGroupsRepository){
        this.productGroupsRepository=productGroupsRepository;
        this.productRepository=productRepository;
    }
    @Override
    public List<Product> getRecommendations(int productId) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product Id not found");
        }
        Product product = productOptional.get();

        List<ProductGroup> productGroupList = productGroupsRepository.findByProductsContaining(product);
        List<Product> productList = productGroupList.stream()
                                    .flatMap(productGroup -> productGroup.getProducts().stream())
                                    .distinct()
                                    .filter(product1 -> product1.getId() != productId)
                                    .collect(Collectors.toList());
        return productList;
    }
}
