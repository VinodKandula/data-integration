package complaints;

import complaints.query.CommentQueryObject;
import complaints.query.CommentQueryObjectRepository;
import complaints.query.ComplaintQueryObject;
import complaints.query.ComplaintQueryObjectRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ComplaintEventProcessor {

	private final ComplaintQueryObjectRepository complaints;
	private Log log = LogFactory.getLog(getClass());

	private final CommentQueryObjectRepository comments;

	@Autowired
	ComplaintEventProcessor(
			ComplaintQueryObjectRepository complaints,
			CommentQueryObjectRepository comments) {
		this.complaints = complaints;
		this.comments = comments;
	}

	@EventHandler
	public void on(CommentAddedEvent cae) {
		ComplaintQueryObject complaint = this.complaints.findOne(cae.getComplaintId());
		CommentQueryObject comment = new CommentQueryObject(complaint,
				cae.getCommentId(), cae.getComment(), cae.getUser(), cae.getWhen());
		this.comments.save(comment);
		this.log.info("created comment " + comment);
	}

	@EventHandler
	public void on(ComplaintFiledEvent cfe) {
		ComplaintQueryObject complaint = new ComplaintQueryObject(cfe.getId(), cfe.getComplaint(),
				cfe.getCompany(), Collections.emptySet());
		this.complaints.save(complaint);
		this.log.info("created complaint " + complaint);
	}
}
