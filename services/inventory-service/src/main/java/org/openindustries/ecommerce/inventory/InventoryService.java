package org.openindustries.ecommerce.inventory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InventoryService {

    @Inject
    InventoryRepository repository;

    public InventoryPool open(OpenInventoryPoolRequest request) {
        return repository.saveAccount(InventoryPool.open(request.sku(), request.availableUnits()));
    }

    public InventoryPool get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "InventoryPool not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public Reservation consume(UUID accountId, CreateReservationRequest request) {
        InventoryPool account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "InventoryPool is not ACTIVE");
        }
        if (request.units() > account.availableUnits) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.units()
                    + " exceeds available " + account.availableUnits);
        }
        account.availableUnits -= request.units();
        repository.saveAccount(account);
        return repository.saveRecord(Reservation.of(accountId, request.channel(), request.units(), account.availableUnits));
    }

    public List<Reservation> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
