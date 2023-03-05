package com.flexpag.paymentscheduler;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import com.flexpag.paymentscheduler.controller.PaymentController;
import com.flexpag.paymentscheduler.model.Payment;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import com.flexpag.paymentscheduler.utils.Status;



@WebMvcTest(PaymentController.class)
public class PaymentControllerTests {

    @MockBean
    private PaymentRepository paymentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldCreatePayment() throws Exception {
        Payment payment = new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Map<String, Object> request = new HashMap<>();
        request.put("scheduledTo", payment.getScheduledTo());
        request.put("amount", payment.getAmount());

        mockMvc.perform(post("/api/payments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(payment.getId()));
    }

    @Test
    void shouldReturnListOfPayments() throws Exception {
        List<Payment> payments = new ArrayList<>(
                Arrays.asList(new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000),
                        new Payment(LocalDateTime.of(2013, Month.MARCH, 15, 05, 20, 04), 50.20f),
                        new Payment(LocalDateTime.of(2077, Month.DECEMBER, 31, 04, 25, 50), 0.5f)));

        when(paymentRepository.findAll()).thenReturn(payments);
        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.payments.size()").value(payments.size()));
    }

    @Test
    void shouldReturnListOfPaymentsWithFilter() throws Exception {
        Status queryStatus = Status.PENDING;
        List<Payment> payments = new ArrayList<>(
                Arrays.asList(new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000),
                        new Payment(LocalDateTime.of(2077, Month.DECEMBER, 31, 04, 25, 50), 2000)));

        when(paymentRepository.findByStatus(queryStatus)).thenReturn(payments);
        mockMvc.perform(get("/api/payments?status=" + queryStatus))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.payments.size()").value(payments.size()));
    }

    @Test
    void shouldReturnPaymentStatus() throws Exception {
        Payment payment = new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000);
        payment.setStatus(Status.PAID);
        long id = payment.getId();
        Status status = payment.getStatus();

        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));
        mockMvc.perform(get("/api/payments/status/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value(status.toString()));
    }

    @Test
    void shouldReturnNotFoundPayment() throws Exception {
        long id = 1L;

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/payments/status/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdatePaymentStatus() throws Exception {
        Payment payment = new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000);
        Long id = payment.getId();
        Payment updatedPayment = new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000);
        updatedPayment.setStatus(Status.PAID);


        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(updatedPayment);

        Map<String, Status> request = new HashMap<>();
        request.put("status", updatedPayment.getStatus());

        mockMvc.perform(patch("/api/payments/status/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.payment.status").value(updatedPayment.getStatus().toString()))
                .andExpect(jsonPath("$.data.payment.id").value(updatedPayment.getId()));
    }

    @Test
    void shouldNotUpdatePaymentStatus() throws Exception {
        Payment payment = new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000);
        Long id = payment.getId();
        payment.setStatus(Status.PAID);

        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        Map<String, Status> request = new HashMap<>();
        request.put("status", Status.PAID);

        mockMvc.perform(patch("/api/payments/status/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldUpdatePayment() throws Exception {
        Payment payment = new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000);
        Long id = payment.getId();
        Payment updatedPayment = new Payment(LocalDateTime.of(2025, Month.JANUARY, 31, 02, 06, 50), 4000);


        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(updatedPayment);

        Map<String, Object> request = new HashMap<>();
        request.put("scheduledTo", payment.getScheduledTo());
        request.put("amount", payment.getAmount());

        mockMvc.perform(put("/api/payments/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.payment.scheduledTo").value(updatedPayment.getScheduledTo().toString()))
                .andExpect(jsonPath("$.data.payment.amount").value(updatedPayment.getAmount()))
                .andExpect(jsonPath("$.data.payment.id").value(updatedPayment.getId()));
    }

    @Test
    void shouldNotUpdatePayment() throws Exception {
        Payment payment = new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000);
        Long id = payment.getId();
        payment.setStatus(Status.PAID);

        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        Map<String, Object> request = new HashMap<>();
        request.put("scheduledTo", payment.getScheduledTo());
        request.put("amount", payment.getAmount());

        mockMvc.perform(put("/api/payments/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldDeletePayment() throws Exception {
        Payment payment = new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000);
        Long id = payment.getId();

        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));
        doNothing().when(paymentRepository).deleteById(id);

        mockMvc.perform(delete("/api/payments/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeletePayment() throws Exception {
        Payment payment = new Payment(LocalDateTime.of(2022, Month.APRIL, 25, 22, 59, 00), 6000);
        payment.setStatus(Status.PAID);
        Long id = payment.getId();

        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        mockMvc.perform(delete("/api/payments/{id}", id))
                .andExpect(status().isForbidden());
    }

}
