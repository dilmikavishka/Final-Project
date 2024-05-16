package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDetail {
    private String batId;
    private String matId;
    private int qty;

    public static boolean save(List<MaterialDetail> odList) throws SQLException {
        for (MaterialDetail md : odList) {
            boolean isSaved = save(md);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(MaterialDetail md) throws SQLException {
        String sql = "INSERT INTO material_details (batchId, materialId, qty) VALUES (?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, md.getBatId());
        pstm.setString(2, md.getMatId());
        pstm.setString(3, String.valueOf(md.getQty()));

        return pstm.executeUpdate() > 0;
    }

}
