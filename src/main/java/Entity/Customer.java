package Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Customer {

    private String id;
    private String name;
    private String nic;
    private String email;
    private String address;
    private String tel;
}
