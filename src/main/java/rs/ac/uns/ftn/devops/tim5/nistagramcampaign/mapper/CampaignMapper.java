package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.AdvertisementDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.CampaignDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Advertisement;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.enums.CampaignEnum;

import java.util.Collection;
import java.util.stream.Collectors;

public class CampaignMapper {

    private CampaignMapper() {}

    public static Campaign toEntity(CampaignDTO requestDTO) {
        Collection<Advertisement> advertisements =
                requestDTO.getAdvertisements()
                        .stream().map(entity-> AdvertisementMapper.toEntity(entity))
                        .collect(Collectors.toList());

        CampaignEnum campaignType = CampaignEnum.SINGLE;

        if (requestDTO.getEndDate() != null && requestDTO.getNumShowsPerDay() > 1) {
            campaignType = CampaignEnum.MULTIPLE;
        }
        return new Campaign(null, advertisements,
                            requestDTO.getStartDate(), requestDTO.getEndDate(),
                            requestDTO.getNumShowsPerDay(), campaignType, null);
    }

    public static CampaignDTO toDTO(Campaign campaign) {
        Collection<AdvertisementDTO> advertisements =
                campaign.getAdvertisements()
                        .stream().map(entity-> AdvertisementMapper.toDTO(entity))
                        .collect(Collectors.toList());
        return new CampaignDTO(advertisements,
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getNumShowsPerDay());
    }
}
