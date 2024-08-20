package Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Customer {

    private String cus_id;
    private String name;
    private String nic;
    private String email;
    private String address;
    private String tel;

    public Customer(String name,String nic,String email,String address,String tel){
        this.name = name;
        this.nic = nic;
        this.email = email;
        this.address = address;
        this.tel = tel;
    }
}
