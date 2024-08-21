package Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Item {
    private String item_id;
    private String name;
    private String  qty;
    private String price;

    public Item(String name,String qty,String price){
        this.name = name;
        this.qty = qty;
        this.price = price;

    }
}
