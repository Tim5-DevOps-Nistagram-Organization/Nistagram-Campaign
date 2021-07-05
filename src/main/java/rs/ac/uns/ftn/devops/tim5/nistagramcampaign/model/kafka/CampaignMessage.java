package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.AdvertisementDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Advertisement;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.enums.CampaignEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CampaignMessage extends Message{

    private Long campaignId;
    private Date startDate;
    private Date endDate;
    private int numShowsPerDay;
    @Enumerated(EnumType.STRING)
    private CampaignEnum type;
    private String agentUsername;
    private Collection<AdvertisementDTO> advertisements;


    public CampaignMessage(String topic, String replayTopic, String action,
                           Long campaignId, Date startDate,
                           Date endDate, int numShowsPerDay, CampaignEnum type,
                           String agentUsername,
                           Collection<AdvertisementDTO> advertisements) {
        super(topic, replayTopic, action);
        this.campaignId = campaignId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numShowsPerDay = numShowsPerDay;
        this.type = type;
        this.agentUsername = agentUsername;
        this.advertisements = advertisements;
    }

    public CampaignMessage(String topic, String replayTopic, String action,
                           Long campaignId) {
        super(topic, replayTopic, action);
        this.campaignId = campaignId;
    }

}
