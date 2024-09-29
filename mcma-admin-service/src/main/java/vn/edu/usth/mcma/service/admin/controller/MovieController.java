package vn.edu.usth.mcma.service.admin.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.usth.mcma.service.common.dto.response.CommonResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MovieController {
    // TODO
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public ResponseEntity<List<Movie>>
}
