package com.flexpag.paymentscheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import com.flexpag.paymentscheduler.utils.APIResponse;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import com.flexpag.paymentscheduler.model.Payment;
import com.flexpag.paymentscheduler.utils.Status;



@RestController
@RequestMapping(path = "/api", consumes = "application/json", produces = "application/json")
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping(path = "/payments", consumes = "*/*")
    public ResponseEntity<APIResponse> getAllPayments(@RequestParam(required = false) Status status){
        try {
            List<Payment> payments = new ArrayList<>();
            if (status == null){
                paymentRepository.findAll().forEach(payments::add);
            }
            else{
                paymentRepository.findByStatus(status).forEach(payments::add);
            }
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new APIResponse("payments", payments), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/payments/status/{id}", consumes = "*/*")
    public ResponseEntity<APIResponse> getPaymentStatus(@PathVariable("id") long id){
        try{
            Optional<Payment> paymentData = paymentRepository.findById(id);
            if (paymentData.isPresent()){
                return new ResponseEntity<>(new APIResponse("status", paymentData.get().getStatus()), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/payments")
    public ResponseEntity<APIResponse> createPayment(@RequestBody Map<String, LocalDateTime> payload){
        try {
            Payment _payment = paymentRepository
                    .save(new Payment(payload.get("scheduledTo")));
            return new ResponseEntity<>(new APIResponse("id", _payment.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(path = "/payments/status/{id}")
    public ResponseEntity<APIResponse> updatePaymentStatus(@PathVariable("id") long id, @RequestBody Map<String, Status> payload){
        try{
            Optional<Payment> paymentData = paymentRepository.findById(id);
            if (paymentData.isPresent()){
                Payment _payment = paymentData.get();
                if (_payment.getStatus() == Status.PENDING){
                    _payment.setStatus(payload.get("status"));
                    return new ResponseEntity<>(new APIResponse("payment", paymentRepository.save(_payment)), HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(path = "/payments/schedule/{id}")
    public ResponseEntity<APIResponse> updatePaymentSchedule(@PathVariable("id") long id, @RequestBody Map<String, LocalDateTime> payload){
        try{
            Optional<Payment> paymentData = paymentRepository.findById(id);
            if (paymentData.isPresent()){
                Payment _payment = paymentData.get();
                if (_payment.getStatus() == Status.PENDING){
                    _payment.setScheduledTo(payload.get("scheduledTo"));
                    return new ResponseEntity<>(new APIResponse("payment", paymentRepository.save(_payment)), HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/payments/{id}", consumes = "*/*")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable("id") long id){
        try{
            Optional<Payment> paymentData = paymentRepository.findById(id);
            if (paymentData.isPresent()){
                Payment _payment = paymentData.get();
                if (_payment.getStatus() == Status.PENDING){
                    paymentRepository.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                else{
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
