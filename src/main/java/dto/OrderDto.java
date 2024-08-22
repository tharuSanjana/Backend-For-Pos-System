package dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderDto {
    private String o_id;
    private Date date;
    private String c_id;
    private String c_name;
    private String c_nic;
    private String c_email;
    private String c_address;
    private String c_tel;
    private String i_id;
    private String i_name;
    private String i_price;
    private String i_qty;
    private String i_selectedQty;
    private Double total;
    private Double balance;
    private Double cash;
    private Double discount;




}
