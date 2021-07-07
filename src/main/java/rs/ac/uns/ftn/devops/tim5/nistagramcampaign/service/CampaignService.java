package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.service;

import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;

import java.util.Collection;

public interface CampaignService {

    Campaign findById(Long id) throws ResourceNotFoundException;

    Campaign create(Campaign campaign, String agentUsername);

    void delete(Long id) throws ResourceNotFoundException;

    Collection<Campaign> getAllActiveByAgent(String agentUsername);
}
