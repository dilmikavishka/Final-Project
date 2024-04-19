package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String oId;
    private String date;
    private String CusId;
    private String payId;

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public String getCusId() {
        return CusId;
    }

    public void setCusId(String cusId) {
        CusId = cusId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
    public String toString() {
        return "Orders{oId='" + this.oId + "', date='" + this.date + "', CusId='" + this.CusId + "', payId='" + this.payId + "'}";
    }

    public String getdate() {
        return date;
    }
}
