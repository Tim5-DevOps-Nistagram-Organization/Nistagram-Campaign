package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.Campaign;

import java.util.Collection;
import java.util.Date;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("SELECT r FROM Campaign r WHERE r.agentUsername = ?1 AND r.startDate <= ?2 AND r.endDate >= ?3")
    Collection<Campaign> getAllActiveByAgent(String agent, Date startTime, Date endTime);
}
