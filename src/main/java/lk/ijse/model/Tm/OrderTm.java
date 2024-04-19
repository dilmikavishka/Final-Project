package lk.ijse.model.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderTm {
    private String oId;
    private String date;
    private String CusId;
    private String payId;
}
