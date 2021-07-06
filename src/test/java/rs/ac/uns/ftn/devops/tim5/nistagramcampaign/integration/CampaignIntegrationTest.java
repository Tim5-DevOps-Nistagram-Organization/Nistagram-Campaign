package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.integration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.constants.AdvertismentConstants;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.constants.CampaignConstants;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.constants.UserConstants;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.AdvertisementDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.dto.CampaignDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.mapper.CampaignMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.enums.CampaignEnum;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.service.CampaignService;


import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class CampaignIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CampaignService campaignService;

    /*
    *  Method test multiple Campaign creation
    *
    *
    * */
    @Test
    public void testCreateCampaignMultiple_Success() {

        Date START_DATE = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);

        Date END_DATE = calendar.getTime();

        AdvertisementDTO a = new AdvertisementDTO(AdvertismentConstants.WEB_SITE_URL, AdvertismentConstants.MEDIA_ID);

        CampaignDTO campaignDTO = new CampaignDTO(new ArrayList<>(Arrays.asList(a)),
                START_DATE, END_DATE, CampaignConstants.NUM_SHOWS_PER_DAY_MULTIPLE);
        Campaign campaign = campaignService.create(CampaignMapper.toEntity(campaignDTO), UserConstants.USERNAME1);

        assertNotNull(campaign.getId());
        assertEquals(CampaignEnum.MULTIPLE.toString(), campaign.getType().toString());
        assertEquals(CampaignConstants.NUM_SHOWS_PER_DAY_MULTIPLE, campaign.getNumShowsPerDay());
        assertEquals(UserConstants.USERNAME1, campaign.getAgentUsername());
    }


    /*
     *  Method test single Campaign creation
     *
     *
     * */
    @Test
    public void testCreateCampaignSingle_Success() {

        Date START_DATE = new Date();

        AdvertisementDTO a = new AdvertisementDTO(AdvertismentConstants.WEB_SITE_URL, AdvertismentConstants.MEDIA_ID);

        CampaignDTO campaignDTO = new CampaignDTO(new ArrayList<>(Arrays.asList(a)),
                START_DATE, null, CampaignConstants.NUM_SHOWS_PER_DAY_SINGLE);
        Campaign campaign = campaignService.create(CampaignMapper.toEntity(campaignDTO), UserConstants.USERNAME1);

        assertNotNull(campaign.getId());
        assertEquals(CampaignEnum.SINGLE.toString(), campaign.getType().toString());
        assertEquals(CampaignConstants.NUM_SHOWS_PER_DAY_SINGLE, campaign.getNumShowsPerDay());
        assertEquals(UserConstants.USERNAME1, campaign.getAgentUsername());
    }

    /*
     *  Method test fetching all active campaign by agent username
     *
     *  Crated 2 campaign (one SINGLE and ACTIVE, second Multiple and PAST)
     *
     *  Should return only SINGLE because it's only active
     *
     * */
    @Test
    public void testGetAllActiveByAgent_Success() throws URISyntaxException {

        Date START_DATE = new Date();

        AdvertisementDTO a = new AdvertisementDTO(AdvertismentConstants.WEB_SITE_URL, AdvertismentConstants.MEDIA_ID);

        CampaignDTO campaignDTO = new CampaignDTO(new ArrayList<>(Arrays.asList(a)),
                START_DATE, null, CampaignConstants.NUM_SHOWS_PER_DAY_SINGLE);
        Campaign campaign = campaignService.create(CampaignMapper.toEntity(campaignDTO), UserConstants.USERNAME1);


        // multiple campaign but not active
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -20);
        Date START_DATE1 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, -20);
        Date END_DATE = calendar.getTime();

        campaignDTO = new CampaignDTO(new ArrayList<>(Arrays.asList(a)),
                START_DATE1, END_DATE, CampaignConstants.NUM_SHOWS_PER_DAY_MULTIPLE);
        campaign = campaignService.create(CampaignMapper.toEntity(campaignDTO), UserConstants.USERNAME1);

        Collection<Campaign> retVal = campaignService.getAllActiveByAgent(UserConstants.USERNAME1);

        assertEquals(1 ,retVal.size());
        Campaign cmpg = retVal.stream().findFirst().get();

        assertEquals(CampaignConstants.NUM_SHOWS_PER_DAY_SINGLE, cmpg.getNumShowsPerDay());
        assertEquals(START_DATE, cmpg.getStartDate());
        assertEquals(CampaignEnum.SINGLE.toString(), cmpg.getType().toString());


    }





}
