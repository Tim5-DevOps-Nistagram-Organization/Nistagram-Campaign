package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.kafka.saga;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.kafka.Constants;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.kafka.CampaignMessage;


@Service
public class CampaignOrchestrator {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    @Autowired
    public CampaignOrchestrator(KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    @Async
    public void startSaga(Campaign campaign, String action) {
        CampaignMessage message = null;
        if (action.equals(Constants.DELETE_ACTION)) {
            message = new CampaignMessage(Constants.SEARCH_TOPIC, Constants.CAMPAIGN_ORCHESTRATOR_TOPIC,
                    action, campaign.getId());
        } else {
            message = new CampaignMessage(Constants.SEARCH_TOPIC, Constants.CAMPAIGN_ORCHESTRATOR_TOPIC,
                    action, campaign.getId(), campaign.getStartDate(), campaign.getEndDate(),
                    campaign.getNumShowsPerDay(), campaign.getType(), campaign.getAgentUsername());
        }
        this.kafkaTemplate.send(message.getTopic(), gson.toJson(message));
    }

    @KafkaListener(topics = Constants.CAMPAIGN_ORCHESTRATOR_TOPIC, groupId = Constants.GROUP)
    public void getMessageOrchestrator(String msg) {
        CampaignMessage message = gson.fromJson(msg, CampaignMessage.class);
        if (message.getAction().equals(Constants.ERROR_ACTION)) {
            message.setDetails(Constants.CAMPAIGN_TOPIC, Constants.CAMPAIGN_ORCHESTRATOR_TOPIC, Constants.ROLLBACK_ACTION);
            kafkaTemplate.send(message.getTopic(), gson.toJson(message));
        }
    }
}
