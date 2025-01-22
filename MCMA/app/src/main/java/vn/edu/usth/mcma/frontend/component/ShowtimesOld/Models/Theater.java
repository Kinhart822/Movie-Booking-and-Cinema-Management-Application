package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Theater implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String cityName;//todo
    private int imageResId;//todo
}
