package lk.ijse.model.Tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchCostTm {
    private String batId;
    private String matId;
    private int qty;
    private double price;
    private double total;
    private JFXButton btnRemove;
}
