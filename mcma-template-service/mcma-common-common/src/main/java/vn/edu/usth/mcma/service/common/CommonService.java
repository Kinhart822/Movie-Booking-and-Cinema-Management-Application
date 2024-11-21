package vn.edu.usth.mcma.service.common;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.usth.mcma.service.common.constants.ApiResponseCode;
import vn.edu.usth.mcma.service.common.exception.BusinessException;

@AllArgsConstructor
@Transactional
public abstract class CommonService<T, ID> {
    private final JpaRepository<T, ID> repository;
    public T findById(ID id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }
}
