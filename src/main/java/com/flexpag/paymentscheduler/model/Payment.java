package com.flexpag.paymentscheduler.model;



import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

import com.flexpag.paymentscheduler.utils.Status;

@Entity
@NoArgsConstructor
public class Payment {
    @Id @GeneratedValue
    @Getter private long id;

    @Getter @Setter private LocalDateTime scheduledTo;

    @Getter @Setter private float amount;
    @Enumerated(EnumType.STRING)
    @Getter @Setter private Status status;

    public Payment(LocalDateTime scheduledTo, float amount){
        if(amount < 0){
            throw new IllegalArgumentException("Amount should be positive");
        }
        this.scheduledTo = scheduledTo;
        this.amount = amount;
        this.status = Status.PENDING;
    }

    @Override
    public String toString() {
        return "Payment [id=" + id + ", scheduledTo=" + scheduledTo + ", amount=" + amount + ", status=" + status + "]";
    }


}
