package vn.edu.usth.mcma.service.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.usth.mcma.service.common.constants.ApiResponseCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDTO {

    private String status;
    private String message;

    public static CommonResponseDTO successResponse() {
        CommonResponseDTO commonResponseDTO = new CommonResponseDTO();
        commonResponseDTO.setStatus(ApiResponseCode.SUCCESS.getStatus());
        commonResponseDTO.setMessage(ApiResponseCode.SUCCESS.getMessage());
        return commonResponseDTO;
    }
}