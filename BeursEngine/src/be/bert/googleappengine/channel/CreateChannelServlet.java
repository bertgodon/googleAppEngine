package be.bert.googleappengine.channel;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@SuppressWarnings("serial")
public class CreateChannelServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("creating token");
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel(ChannelKeys.OVERVIEW_KEY);
	    
	    resp.setContentType("text/plain");
	    resp.getWriter().write(token);
	}
}