package rs.ac.uns.ftn.devops.tim5.nistagramcampaign.kafka;

public class Constants {

    public static final String GROUP = "my_group_id";
    public static final String AUTH_TOPIC = "auth_topic";
    public static final String AUTH_CAMPAIGN_TOPIC = "auth_campaign_topic";


    public static final String CAMPAIGN_ORCHESTRATOR_TOPIC = "campaign_orchestrator_topic";
    public static final String SEARCH_TOPIC = "search_topic";
    public static final String CAMPAIGN_TOPIC = "campaign_topic";


    public static final String START_ACTION = "start_action";
    public static final String UPDATE_ACTION = "update_action";
    public static final String DELETE_ACTION = "delete_action";
    public static final String DONE_ACTION = "done_action";
    public static final String ERROR_ACTION = "error_action";
    public static final String ROLLBACK_ACTION = "rollback_action";
    public static final String ROLLBACK_DONE_ACTION = "rollback_done_action";
}
