package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO {
    @NotNull(message = "Website Url can not be null")
    @NotBlank(message = "Website Url can not be blank")
    private String websiteUrl;

    @NotNull(message = "Media can not be null")
    private Long mediaId;
}
