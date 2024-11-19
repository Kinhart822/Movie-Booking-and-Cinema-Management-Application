package vn.edu.usth.mcma.service.admin.service;

import constants.EntityStatus;
import vn.edu.usth.mcma.service.admin.dto.CinemaRequest;
import vn.edu.usth.mcma.service.admin.repository.CinemaRepository;
import vn.edu.usth.mcma.service.common.CommonResponse;
import vn.edu.usth.mcma.service.common.CommonService;
import vn.edu.usth.mcma.service.common.dao.Cinema;

import java.util.List;

public class CinemaService extends CommonService<Cinema, Long> {
    private final CinemaRepository cinemaRepository;
    public CinemaService(CinemaRepository cinemaRepository) {
        super(cinemaRepository);
        this.cinemaRepository = cinemaRepository;
    }
    public CommonResponse createCinema(CinemaRequest request) {
        Cinema cinema = new Cinema();
        cinema.setCityId(request.getCityId());
        cinema.setName(request.getName());
        cinema.setStatus(EntityStatus.CREATED.getStatus());
        //TODO
//        cinema.setCreatedBy();
//        cinema.setLastModifiedBy();
        cinemaRepository.save(cinema);
        return CommonResponse.successResponse();
    }
    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }
    public CommonResponse updateCinema(Long id, CinemaRequest request) {
        Cinema cinema = findById(id);
        // changing cityId is not allowed think about it :)
        cinema.setName(request.getName());
        //TODO
//        cinema.setLastModifiedBy();
        cinemaRepository.save(cinema);
        return CommonResponse.successResponse();
    }
    public CommonResponse deleteCinema(Long id) {
        Cinema cinema = findById(id);
        cinema.setStatus(EntityStatus.DELETED.getStatus());
        cinemaRepository.save(cinema);
        return CommonResponse.successResponse();
    }
}
