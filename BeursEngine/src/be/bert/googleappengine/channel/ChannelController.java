package be.bert.googleappengine.channel;

import java.util.List;

import javax.inject.Named;

import org.eclipse.jetty.util.ajax.JSON;

import be.bert.googleappengine.controller.DrinkService;
import be.bert.googleappengine.model.Beverage;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Named
public class ChannelController {

	public void sendUpdateToOverView(List<Beverage> beverages){
		ChannelService channelService = ChannelServiceFactory.getChannelService();
  		channelService.sendMessage(new ChannelMessage(ChannelKeys.OVERVIEW_KEY, JSON.toString(beverages)));
	}
	
	public void sendUpdateToOverView() throws JSONException{
		DrinkService drinkService = new DrinkService();
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		JSONObject message = new JSONObject("bert has send a message");

		channelService.sendMessage(new ChannelMessage(ChannelKeys.OVERVIEW_KEY, message.toString()));
	}
}
