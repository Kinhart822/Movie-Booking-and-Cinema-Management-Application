package vn.edu.usth.mcma.service.admin.service;

import constants.EntityStatus;
import jakarta.transaction.Transactional;
import org.hibernate.loader.ast.internal.CacheEntityLoaderHelper;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.service.admin.dto.CityRequest;
import vn.edu.usth.mcma.service.admin.repository.CityRepository;
import vn.edu.usth.mcma.service.common.CommonResponse;
import vn.edu.usth.mcma.service.common.CommonService;
import vn.edu.usth.mcma.service.common.dao.City;

import java.util.List;


@Transactional
@Service
public class CityService extends CommonService<City, Long> {
    private final CityRepository cityRepository;
    public CityService(CityRepository cityRepository) {
        super(cityRepository);
        this.cityRepository = cityRepository;
    }
    public CommonResponse createCity(CityRequest request) {
        City city = new City();
        city.setName(request.getName());
        city.setStatus(EntityStatus.CREATED.getStatus());
        //TODO
//        city.setCreatedBy();
//        city.setLastModifiedBy();
        cityRepository.save(city);
        return CommonResponse.successResponse();
    }
    public List<City> findAll() {
        return cityRepository.findAll();
    }
    public CommonResponse updateCity(Long id, CityRequest request) {
        City city = findById(id);
        city.setName(request.getName());
        //TODO
//        city.setLastModifiedBy();
        cityRepository.save(city);
        return CommonResponse.successResponse();
    }
    public CommonResponse deleteCity(Long id) {
        City city = findById(id);
        city.setStatus(EntityStatus.DELETED.getStatus());
        cityRepository.save(city);
        return CommonResponse.successResponse();
    }
}
