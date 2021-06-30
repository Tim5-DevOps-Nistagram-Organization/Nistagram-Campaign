package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.CampaignDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.kafka.Constants;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.kafka.saga.CampaignOrchestrator;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.mapper.CampaignMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.service.CampaignService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private CampaignService campaignService;
    private CampaignOrchestrator campaignOrchestrator;

    @Autowired
    public CampaignController(CampaignService campaignService,
                              CampaignOrchestrator campaignOrchestrator) {
        this.campaignService = campaignService;
        this.campaignOrchestrator = campaignOrchestrator;
    }

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<String> create(@Valid @RequestBody CampaignDTO campaignRequestDTO,
                                               Principal principal) {
        Campaign campaign = campaignService.create(CampaignMapper.toEntity(campaignRequestDTO), principal.getName());
        campaignOrchestrator.startSaga(campaign, Constants.START_ACTION);
        return new ResponseEntity<>("You successfully created campaign", HttpStatus.OK);
    }

    @GetMapping(path="/multiple/{agentUsername}")
    public ResponseEntity<Collection<CampaignDTO>> getAllMultipleByAgent(@PathVariable String agentUsername) {
        Collection<CampaignDTO> retVal =
                campaignService.getAllMultipleByAgent(agentUsername)
                        .stream().map(camp -> CampaignMapper.toDTO(camp))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(path="/single/{agentUsername}")
    public ResponseEntity<Collection<CampaignDTO>> getAllSingleByAgent(@PathVariable String agentUsername) {
        Collection<CampaignDTO> retVal =
                campaignService.getAllSingleByAgent(agentUsername)
                        .stream().map(camp -> CampaignMapper.toDTO(camp))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{campaignId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_AGENT')")
    public ResponseEntity<String> delete(@Valid @PathVariable Long campaignId) throws ResourceNotFoundException {
        campaignService.delete(campaignId);
        Campaign campaign = new Campaign(campaignId);
        campaignOrchestrator.startSaga(campaign, Constants.DELETE_ACTION);
        return new ResponseEntity<>("Campaign is successfully removed.", HttpStatus.OK);
    }


}
