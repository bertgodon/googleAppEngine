package be.bert.googleappengine.channel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@Api(name = "channelService",
version = "v1")
@Named
public class ChannelAPIService {

    @ApiMethod(name = "getToken", httpMethod="GET")
    public List<String> getChannelToken(){
    	ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel(ChannelKeys.OVERVIEW_KEY);
    	return Arrays.asList(token);
    }
}
