package rs.ac.uns.ftn.devops.tim5.nistagramcampaign;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class NistagramCampaignApplicationTests {

    @Test
    void contextLoads() {
        int a = 1, b = 1;
        int sum = a + b;
        assertEquals(2, sum);
    }

}
