package lk.ijse.model.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentTm {
    private String paymentId;
    private String paymentDate;
    private double  amount;
    private String type;

}
