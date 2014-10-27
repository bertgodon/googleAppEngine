package be.bert.googleappengine.channel;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class OrderServlet extends HttpServlet {
	private static final String ORDER_PARAMETER = "order";

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String message = req.getParameter(ORDER_PARAMETER);
		System.out.println("orderServlet");
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		Gson gson = new Gson();
		channelService.sendMessage(new ChannelMessage(ChannelKeys.OVERVIEW_KEY, gson.toJson(message)));
	}
}