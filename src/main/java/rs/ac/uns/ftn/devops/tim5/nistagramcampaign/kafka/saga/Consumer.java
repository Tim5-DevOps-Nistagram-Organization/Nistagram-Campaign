package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.kafka.saga;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.kafka.Constants;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.kafka.CampaignMessage;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.kafka.Message;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.service.CampaignService;

@Service
public class Consumer {

    private final CampaignService campaignService;
    private final Gson gson;

    @Autowired
    public Consumer(Gson gson, CampaignService campaignService
    ) {
        this.gson = gson;
        this.campaignService = campaignService;
    }

    @KafkaListener(topics = Constants.CAMPAIGN_TOPIC, groupId = Constants.GROUP)
    public void getMessage(String msg) throws ResourceNotFoundException {
        Message message = gson.fromJson(msg, Message.class);
        if (message.getReplayTopic().equals(Constants.CAMPAIGN_ORCHESTRATOR_TOPIC) &&
                message.getAction().equals(Constants.ROLLBACK_ACTION)) {
            CampaignMessage campaignMessage = gson.fromJson(msg, CampaignMessage.class);
            Campaign campaign = campaignService.findById(campaignMessage.getCampaignId());
            campaignService.delete(campaign.getId());
        }
    }
}
