package lk.ijse.model.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MatirialTm {
    private String id;
    private String name;
    private String date;
    private double matQty;
    private String supId;
}
