package lk.ijse.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchMachine {
    private String MaId;
    private String BaId;
    private Date date;
}
