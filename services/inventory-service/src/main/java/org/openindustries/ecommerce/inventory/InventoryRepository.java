package org.openindustries.ecommerce.inventory;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class InventoryRepository {
    private final ConcurrentHashMap<UUID, InventoryPool> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<Reservation> records = new CopyOnWriteArrayList<>();

    public InventoryPool saveAccount(InventoryPool account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<InventoryPool> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public Reservation saveRecord(Reservation record) {
        records.add(record);
        return record;
    }

    public List<Reservation> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
