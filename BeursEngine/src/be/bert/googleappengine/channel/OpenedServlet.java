// Copyright 2010 Google Inc. All Rights Reserved.

package be.bert.googleappengine.channel;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author moishel@google.com (Your Name Here)
 *
 */
@SuppressWarnings("serial")
public class OpenedServlet extends HttpServlet {
  
	private static final String OK = "ok";
	private static final String CONTENT_TYPE_PLAIN_TEXT = "text/plain";

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType(CONTENT_TYPE_PLAIN_TEXT);
  		resp.getWriter().println(OK);
	}
}
