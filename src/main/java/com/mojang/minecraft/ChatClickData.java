package com.mojang.minecraft;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mojang.minecraft.gui.FontRenderer;

public class ChatClickData {
	public class LinkData {
		public String link;
		public int x0;
		public int x1;

		public LinkData(String s, int a, int b) {
			link = s;
			x0 = a;
			x1 = b;
		}
	}

	private final String urlPattern = "((https?|ftp|sftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	private final Pattern compiledPattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
	public final String message;

	private final ArrayList<LinkData> clickedUrls;

	private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

	public static String stripControlCodes(String string) {
		return patternControlCode.matcher(string).replaceAll("");
	}

	public ChatClickData(FontRenderer fontRenderer, ChatLine chatLine, int x, int y) {
		message = chatLine.message;
		clickedUrls = pullLinks(message, fontRenderer);
	}

	public ArrayList<LinkData> getClickedUrls() {
		return clickedUrls;
	}

	public URI getURI(String message) {
		Matcher urlMatcher = compiledPattern.matcher(message);

		if (urlMatcher.matches()) {
			try {
				String url = urlMatcher.group(0);

				if (urlMatcher.group(1) == null) { // will dis happen?
					url = "http://" + url;
				}

				return new URI(url);
			} catch (URISyntaxException uriE) {

			}
		}
		return null;
	}

	private ArrayList<LinkData> pullLinks(String text, FontRenderer fr) {
		ArrayList<LinkData> links = new ArrayList<LinkData>();
		Matcher m = compiledPattern.matcher(text);
		while (m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
				urlStr = urlStr.substring(1, urlStr.length() - 1);
			}
			links.add(new LinkData(urlStr, fr.getWidth(text.substring(0, m.start())), fr
					.getWidth(text.substring(0, m.end()))));
		}
		return links;
	}
}
