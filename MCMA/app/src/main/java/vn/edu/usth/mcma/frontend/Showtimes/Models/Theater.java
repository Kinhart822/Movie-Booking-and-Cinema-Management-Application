package vn.edu.usth.mcma.frontend.Showtimes.Models;

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
    private String city;
    private int imageResId;
}
