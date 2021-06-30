package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.enums.CampaignEnum;

import java.util.Collection;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("SELECT r FROM Campaign r WHERE r.type = ?1 AND r.agentUsername = ?2")
    Collection<Campaign> getAllByTypeAndAgent(CampaignEnum type, String agent);
}
