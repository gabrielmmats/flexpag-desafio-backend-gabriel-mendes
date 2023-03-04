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
    @Enumerated(EnumType.STRING)
    @Getter @Setter private Status status;

    public Payment(LocalDateTime scheduledTo){
        this.scheduledTo = scheduledTo;
        this.status = Status.PENDING;
    }

    @Override
    public String toString() {
        return "Payment [id=" + id + ", scheduledTo=" + scheduledTo + ", status=" + status + "]";
    }


}
