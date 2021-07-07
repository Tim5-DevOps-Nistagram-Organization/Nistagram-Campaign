package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDetailsDTO {
    private Long id;
    private Collection<AdvertisementDTO> advertisements;
    private Date startDate;
    private Date endDate;
    private int numShowsPerDay;
}
