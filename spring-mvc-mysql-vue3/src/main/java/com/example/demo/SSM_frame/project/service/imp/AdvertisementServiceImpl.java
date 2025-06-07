package com.example.demo.SSM_frame.project.service.imp;


import com.example.demo.SSM_frame.project.map.AdvertisementMapper;
import com.example.demo.SSM_frame.project.pojo.Advertisement;
import com.example.demo.SSM_frame.project.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    public void addAdvertisement(Advertisement advertisement) {
        advertisementMapper.insertAdvertisement(advertisement);
    }

    @Override
    public void updateAdvertisement(Advertisement advertisement) {
        advertisementMapper.updateAdvertisement(advertisement);
    }

    @Override
    public void deleteAdvertisement(Integer advertid) {
        advertisementMapper.deleteAdvertisement(advertid);
    }

    @Override
    public Advertisement getAdvertisementById(Integer advertid) {
        return advertisementMapper.getAdvertisementById(advertid);
    }

    @Override
    public List<Advertisement> getAllAdvertisements() {
        return advertisementMapper.getAllAdvertisements();
    }

    @Override
    public List<Advertisement> getActiveAdvertisements() {
        return advertisementMapper.getActiveAdvertisements();
    }
}
