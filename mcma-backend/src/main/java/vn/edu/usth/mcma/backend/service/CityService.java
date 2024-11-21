package vn.edu.usth.mcma.backend.service;

import constants.EntityStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.CityRequest;
import vn.edu.usth.mcma.backend.repository.CityRepository;
import vn.edu.usth.mcma.backend.dto.CommonResponse;
import vn.edu.usth.mcma.backend.dao.City;

import java.util.List;


@Transactional
@Service
public class CityService extends AbstractService<City, Long> {
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
