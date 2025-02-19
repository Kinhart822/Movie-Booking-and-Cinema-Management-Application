package vn.edu.usth.mcma.frontend.dto.response;

import lombok.Data;

@Data
public class PerformerResponse {
    private Long id;
    private String name;
    private Integer typeId;
    private String dob;
    private Integer sex;
}
