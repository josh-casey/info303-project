package bridge;


import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mark George
 */
public class BridgeService {

	private final static String usercode = "casjo872";
	
	private final static String jmsQueue = "new-sale";
	private final static String sseEvent = "new-sale";

	private final static String uri = "http://isgb.otago.ac.nz/vend/sales/stream/" + usercode;

	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger(BridgeService.class);
		try {
			JMSSender sender = new JMSSender(jmsQueue);
			SSEReceiver receiver = new SSEReceiver(uri, sseEvent, sender);
			receiver.start();
			Thread.currentThread().join();
		} catch (InterruptedException ex) {
			// nobody cares
		} catch (URISyntaxException ex) {
			log.error("SSE Service URI is not valid", ex);
		}
	}

}
