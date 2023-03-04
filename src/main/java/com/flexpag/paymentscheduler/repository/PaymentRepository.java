package com.flexpag.paymentscheduler.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.flexpag.paymentscheduler.model.Payment;
import com.flexpag.paymentscheduler.utils.Status;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStatus (Status status);
}
