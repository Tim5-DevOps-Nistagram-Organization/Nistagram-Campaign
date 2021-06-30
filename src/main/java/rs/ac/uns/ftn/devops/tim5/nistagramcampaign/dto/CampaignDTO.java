package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {

    private Collection<AdvertisementDTO> advertisements;
    @NotNull(message = "start date can not be null")
    private Date startDate;
    private Date endDate;
    private int numShowsPerDay;

}
