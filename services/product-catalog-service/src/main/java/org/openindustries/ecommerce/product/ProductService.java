package org.openindustries.ecommerce.product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository repository;

    public Product create(CreateProductRequest request) {
        return repository.save(Product.create(request.name(), request.category(), request.priceMinor()));
    }

    public Product get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Product not found: " + id));
    }

    public List<Product> list() {
        return repository.findAll();
    }

    public Product publish(UUID id) {
        Product entity = get(id);
        if (!"DRAFT".equals(entity.status)) {
            throw new DomainException(409, "Only a DRAFT product can transition via publish");
        }
        entity.status = "PUBLISHED";
        return repository.save(entity);
    }
}
