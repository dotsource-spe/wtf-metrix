import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.service.MetricServerFeedbackService;
import org.junit.Test;

public class MetricServerFeedbackServiceTest {

    private MetricServerFeedbackService service = new MetricServerFeedbackService();

    @Test
    public void testStoreFeedback()
    {
        FeedbackEntry fe = new FeedbackEntry();
        fe.setUserName("Sven");
        fe.setPath("~/git/myProject/myFile.file");
        service.storeFeedback(fe);
    }
}
