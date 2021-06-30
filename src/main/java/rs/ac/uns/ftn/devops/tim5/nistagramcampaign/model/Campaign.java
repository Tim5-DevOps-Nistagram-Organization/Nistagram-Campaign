package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.devops.tim5.nistagramcampaign.model.enums.CampaignEnum;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn
    private Collection<Advertisement> advertisements;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private int numShowsPerDay;

    @Enumerated(EnumType.STRING)
    private CampaignEnum type;

    private String agentUsername;

    public Campaign(Long id){
        this.id = id;
    }

}
