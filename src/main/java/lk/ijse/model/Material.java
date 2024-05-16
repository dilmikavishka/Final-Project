package lk.ijse.model;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Material {
    private String id;
    private String name;
    private Date date;
    private int matQty;
    private String supId;
    private double price;


}
