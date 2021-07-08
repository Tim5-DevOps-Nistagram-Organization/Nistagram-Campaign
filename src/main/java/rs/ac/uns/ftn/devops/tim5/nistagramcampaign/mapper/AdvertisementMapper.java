package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.AdvertisementDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Advertisement;

public class AdvertisementMapper {

    private AdvertisementMapper() {
    }

    public static Advertisement toEntity(AdvertisementDTO requestDTO) {
        return new Advertisement(null, requestDTO.getWebsiteUrl(), requestDTO.getMediaId());
    }

    public static AdvertisementDTO toDTO(Advertisement advertisement) {
        return new AdvertisementDTO(advertisement.getWebsiteUrl(), advertisement.getMediaId());
    }
}
