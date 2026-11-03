package org.openindustries.ecommerce.storefront;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class StorefrontService {

    @Inject
    StorefrontRepository repository;

    public Storefront create(CreateStorefrontRequest request) {
        return repository.save(Storefront.create(request.name(), request.marketCode(), request.currencyCode()));
    }

    public Storefront get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Storefront not found: " + id));
    }

    public List<Storefront> list() {
        return repository.findAll();
    }

    public Storefront suspend(UUID id) {
        Storefront entity = get(id);
        if ("SUSPENDED".equals(entity.status)) {
            throw new DomainException(409, "Storefront already SUSPENDED");
        }
        entity.status = "SUSPENDED";
        return repository.save(entity);
    }

    public Storefront reactivate(UUID id) {
        Storefront entity = get(id);
        if ("ACTIVE".equals(entity.status)) {
            throw new DomainException(409, "Storefront already ACTIVE");
        }
        entity.status = "ACTIVE";
        return repository.save(entity);
    }
}
