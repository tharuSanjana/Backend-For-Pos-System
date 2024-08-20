package dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CustomerDto implements Serializable {
    private String id;
    private String name;
    private String nic;
    private String email;
    private String address;
    private String tel;

}
