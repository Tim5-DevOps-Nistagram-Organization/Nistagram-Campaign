package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.AdvertisementDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.CampaignDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.CampaignDetailsDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Advertisement;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.enums.CampaignEnum;

import java.util.Calendar;
import java.util.Collection;
import java.util.stream.Collectors;

public class CampaignMapper {

    private CampaignMapper() {
    }

    public static Campaign toEntity(CampaignDTO requestDTO) {
        Collection<Advertisement> advertisements =
                requestDTO.getAdvertisements()
                        .stream().map(AdvertisementMapper::toEntity)
                        .collect(Collectors.toList());

        CampaignEnum campaignType = CampaignEnum.SINGLE;

        if (requestDTO.getEndDate() != null && requestDTO.getNumShowsPerDay() > 1) {
            campaignType = CampaignEnum.MULTIPLE;
        }
        // if SINGLE campaign then end date is tomorrow
        if (requestDTO.getEndDate() == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            requestDTO.setEndDate(calendar.getTime());
        }
        return new Campaign(null, advertisements,
                requestDTO.getStartDate(), requestDTO.getEndDate(),
                requestDTO.getNumShowsPerDay(), campaignType, null);
    }

    public static CampaignDetailsDTO toDTO(Campaign campaign) {
        Collection<AdvertisementDTO> advertisements =
                campaign.getAdvertisements()
                        .stream().map(AdvertisementMapper::toDTO)
                        .collect(Collectors.toList());
        return new CampaignDetailsDTO(campaign.getId(), advertisements,
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getNumShowsPerDay());
    }
}
