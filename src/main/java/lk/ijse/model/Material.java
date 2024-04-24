package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor@Data
public class Material {
    private String id;
    private String name;
    private String date;
    private double matQty;
    private String supId;
}
