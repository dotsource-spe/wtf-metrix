import com.sun.jna.platform.win32.Sspi;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.FileMetricEntry;
import de.dotsource.wtf.service.FeedbackService;
import de.dotsource.wtf.service.MetricServerFeedbackService;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MetricServerFeedbackServiceTest {

    private MetricServerFeedbackService service = new MetricServerFeedbackService();

    @Test
    public void testStoreFeedback()
    {
        FeedbackEntry fe = new FeedbackEntry();
        fe.setUserName("Sven");
        fe.setPath("~/git/myProject/myFile.file");
        fe.setComment("wtf");
        fe.setLine(10);
        fe.setRepository("/git");
        fe.setRevision("12345678 abcdefg");
        fe.setTimeStamp(LocalDateTime.now());
        service.storeFeedback(fe);

        //assertNotNull(service.storeFeedback(fe));
        //assertTrue(service.storeFeedback(fe) instanceof FileMetricEntry);
        //assertNotNull(...);
        //assertEquals(...);
    }
/*
    @Test
    public void testStoreFeedbackWithInvalidArguments()
    {
        FeedbackEntry fe = new FeedbackEntry();
        fe.setUserName("Sven");
        fe.setPath("~/git/myProject/myFile.file");
        fe.setComment("wtf");
        fe.setLine(10);
        fe.setRepository("/git");
        fe.setRevision("12345678 abcdefg");
        fe.setTimeStamp(LocalDateTime.now());
        service.storeFeedback(fe);

        FeedbackEntry invalidPath = fe;
        invalidPath.setPath("not a path");
        //assertFalse(...);

        FeedbackEntry invalidLine = fe;
        invalidLine.setLine(-1);
        //assertFalse(..);

        FeedbackEntry invalidRepository = fe;
        invalidRepository.setRepository("not a repository");
        //assertFalse(...);

        FeedbackEntry invalidRevision = fe;
        invalidRevision.setRevision("not a revision");
        //assertFalse(...);

    }

    @Test(expected = Exception.class)
    public void testStoreFeedbackWithEmptyArguments()
    {
        FeedbackEntry fe = new FeedbackEntry();

        fe.setLine(10);
        service.storeFeedback(fe);

        fe.setUserName("Sven");
        service.storeFeedback(fe);

        fe.setPath("~/git/myProject/myFile.file");
        service.storeFeedback(fe);

        fe.setComment("wtf");
        service.storeFeedback(fe);

        fe.setRepository("/git");
        service.storeFeedback(fe);

        fe.setRevision("12345678 abcdefg");
        service.storeFeedback(fe);

        fe.setTimeStamp(LocalDateTime.now());
        service.storeFeedback(fe);

    }*/
}
