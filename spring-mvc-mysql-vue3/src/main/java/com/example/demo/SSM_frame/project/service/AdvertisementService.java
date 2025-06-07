package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.Advertisement;
import java.util.List;

public interface AdvertisementService {
    void addAdvertisement(Advertisement advertisement);
    void updateAdvertisement(Advertisement advertisement);
    void deleteAdvertisement(Integer advertid);
    Advertisement getAdvertisementById(Integer advertid);
    List<Advertisement> getAllAdvertisements();
    List<Advertisement> getActiveAdvertisements();
}
