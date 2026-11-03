package org.openindustries.ecommerce.product;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class ProductRepository {
    private final ConcurrentHashMap<UUID, Product> store = new ConcurrentHashMap<>();

    public Product save(Product entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Product> findAll() {
        return List.copyOf(store.values());
    }
}
