package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {

    @NotEmpty(message = "Advertisements can not be empty.")
    private Collection<AdvertisementDTO> advertisements;

    @NotNull(message = "Start date can not be null")
    private Date startDate;
    private Date endDate;

    @NotNull(message = "Num of shows per day can not be null")
    @Min(value = 1, message = "Num of shows per day min value is 1")
    private int numShowsPerDay;

}
