package com.service;

import com.entity.Reservation;
import com.entity.Slot;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ReservationService {

    @Inject
    EntityManager em;

    @Transactional
    public void initData(){
        Long slotCount = em.createQuery("SELECT COUNT(s) FROM Slot s", Long.class).getSingleResult();
        if (slotCount == 0) {
            for (int i = 1; i <= 10; i++) {
                Slot slot = new Slot();
                slot.setName("Locul " + i);
                slot.setReserved(false);
                em.persist(slot);
            }
            System.out.println("Added 10 slots to the database.");
        }
    }

    @ActivateRequestContext
    public List<Slot> getAvailableSlots()
    {
        return em.createQuery("SELECT s FROM Slot s WHERE s.reserved = false", Slot.class)
                .getResultList();
    }

    @Transactional
    @ActivateRequestContext
    public String reserveSlot(Long slotId, String customerName, String date)
    {
        Slot slot = em.find(Slot.class, slotId.intValue());
        if (slot == null) {
            return "Error: Slot with ID " + slotId + " does not exist.";
        }
        if (slot.isReserved()) {
            return "Error: Slot is already reserved! Try another one.";
        }

        slot.setReserved(true);
        em.merge(slot);

        Reservation reservation = new Reservation();
        reservation.setCustomerName(customerName);
        reservation.setReservationDate(date);
        reservation.setSlotId((long) slot.getId());
        em.persist(reservation);
        em.flush();
        return "SUCCESS: You have reserved " + slot.getName() + " | Reservation #" + reservation.getId();
    }

    @ActivateRequestContext
    public List<Reservation> getByClient(String token)
    {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.customerName = :token", Reservation.class)
                .setParameter("token", token)
                .getResultList();
    }

    @Transactional
    @ActivateRequestContext
    public void cancelReservation(Long id)
    {
        Reservation reservation = em.find(Reservation.class, id);
        if (reservation != null && reservation.getSlotId() != null) {
            Slot slot = em.find(Slot.class, reservation.getSlotId().intValue());
            if (slot != null) {
                slot.setReserved(false);
            }
            em.remove(reservation);
        }
    }
}
