package org.openindustries.ecommerce.inventory;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Inventory", description = "Guarded inventory pools per SKU and the channel reservations against them.")
public class InventoryResource {

    @Inject
    InventoryService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a inventorypool")
    public Response open(@Valid OpenInventoryPoolRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public InventoryPool get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public Reservation consume(@PathParam("id") UUID id, @Valid CreateReservationRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/reservations")
    @Operation(summary = "List consumption records of an account")
    public List<Reservation> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}
