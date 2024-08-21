package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ItemDto {
    private String item_id;
    private String name;
    private String  qty;
    private String price;


}
