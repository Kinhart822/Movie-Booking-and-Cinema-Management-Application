package vn.edu.usth.mcma.service.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.edu.usth.mcma.service.common.web.rest.response.IApiResponse;

@Getter
@AllArgsConstructor
public enum ApiResponseCode implements IApiResponse {
    SUCCESS("200", "SUCCESS");

    private final String status;
    private final String message;
}
