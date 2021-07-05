package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.service.impl;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.enums.CampaignEnum;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.repository.CampaignRepository;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.service.CampaignService;

import java.util.Collection;
import java.util.Date;

@Service
public class CampaignServiceImpl implements CampaignService {

    private CampaignRepository campaignRepository;

    @Autowired
    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }


    @Override
    public Campaign findById(Long id) throws ResourceNotFoundException {
        return campaignRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Campaign"));
    }

    @Override
    public Campaign create(Campaign campaign, String agentUsername) {
        campaign.setAgentUsername(agentUsername);
        return campaignRepository.save(campaign);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException{
        campaignRepository.delete(this.findById(id));
    }

    @Override
    public Collection<Campaign> getAllSingleByAgent(String agentUsername) {
        return campaignRepository.getAllByTypeAndAgent(CampaignEnum.SINGLE, agentUsername);
    }

    @Override
    public Collection<Campaign> getAllMultipleByAgent(String agentUsername) {
        return campaignRepository.getAllByTypeAndAgent(CampaignEnum.MULTIPLE, agentUsername);
    }

    @Override
    public Collection<Campaign> getAllActiveByAgent(String agentUsername) {
        return campaignRepository.getAllActiveByAgent(agentUsername, new Date(), new Date());
    }
}
