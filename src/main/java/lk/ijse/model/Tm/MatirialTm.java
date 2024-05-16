package lk.ijse.model.Tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MatirialTm {
    private String id;
    private String name;
    private Date date;
    private int matQty;
    private String supId;
    private double price;
    private JFXButton btnSave;
}
