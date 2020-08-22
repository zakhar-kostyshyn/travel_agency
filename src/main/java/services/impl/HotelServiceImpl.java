package services.impl;

import dao.CountryDao;
import dao.HotelDao;
import lombok.Setter;
import models.Country;
import models.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.HotelService;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private HotelDao hotelDao;
    private CountryDao countryDao;

    @Autowired
    public HotelServiceImpl(HotelDao hotelDao, CountryDao countryDao) {
        this.hotelDao = hotelDao;
        this.countryDao = countryDao;
    }

    @Override
    public Set<Hotel> getHotelsFromCountry(long countryId) {
        Country countryEntity = countryDao.findOne(countryId);
        //  TODO custom exception
        if (countryEntity == null) throw new IllegalArgumentException("Country doesn't exist");
        return hotelDao.findAllByCountry(countryEntity);
    }

    @Override
    public Hotel createNewHotel(Hotel hotel, long countryId) {
        Country countryEntity = countryDao.findOne(countryId);
        if (countryEntity == null) throw new IllegalArgumentException("Country doesn't exist");
        hotel.setCountry(countryEntity);
        Long hotelEntityId = hotelDao.save(hotel);
        Hotel hotelEntity = hotelDao.findOne(hotelEntityId);
        countryEntity.getHotels().add(hotelEntity);
        return hotelEntity;
    }

    @Override
    public Hotel getById(long hotelId) {
        Hotel hotelEntity = hotelDao.findOne(hotelId);
        if (hotelEntity == null) throw new IllegalArgumentException("Hotel doesn't exist");
        return hotelEntity;
    }


}
