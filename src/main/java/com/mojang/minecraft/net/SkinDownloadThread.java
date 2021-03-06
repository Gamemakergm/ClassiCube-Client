package com.mojang.minecraft.net;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

public class SkinDownloadThread extends Thread {
	String skinServer;

	private NetworkPlayer player;

	public SkinDownloadThread(NetworkPlayer networkPlayer, String skinServer) {
		super();

		player = networkPlayer;
		this.skinServer = skinServer;
	}

	@Override
	public void run() {
		HttpURLConnection connection = null;

		try {
			connection = (HttpURLConnection) new URL(skinServer
					+ (player.SkinName == null ? player.name : player.SkinName) + ".png")
					.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.76");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(false);

			connection.connect();

			if (connection.getResponseCode() == 404) {
				return;
			}

			player.newTexture = ImageIO.read(connection.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
