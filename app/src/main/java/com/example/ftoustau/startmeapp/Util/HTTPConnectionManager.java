package com.example.ftoustau.startmeapp.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

public class HTTPConnectionManager {
	private HttpURLConnection connection;


	public String getRequestString(String url) throws DAOException{
		String result = null;
		HttpURLConnection connection = null;
		InputStream inputStream = null;

		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setReadTimeout(10000);  //In milliseconds.
			connection.setRequestMethod("GET");
			inputStream = connection.getInputStream();
			result = convertStreamToString(inputStream);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			throw new DAOException("Connection timeout. Please try again later.");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new DAOException("There is no internet connection");
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException("Connection Problem");
		} finally {
			if (inputStream != null){
				try {
					inputStream.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			if (connection != null) {
				connection.disconnect();
			}
		}

		return result;
	}

	public InputStream getRequestStream(String url) throws DAOException{
		InputStream inputStream;

		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setReadTimeout(10000);  //In milliseconds.
			connection.setRequestMethod("GET");
			inputStream = connection.getInputStream();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			throw new DAOException("Connection timeout. Please try again later.");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new DAOException("There is no internet connection");
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException("Connection Problem");
		}

		return inputStream;
	}

	public String convertStreamToString(InputStream inputStream) {

		byte[] bytes = new byte[1000];

		StringBuilder stringBuilder = new StringBuilder();

		Integer numRead;

		try {
			while ((numRead = inputStream.read(bytes)) >= 0) {
				stringBuilder.append(new String(bytes, 0, numRead));
			}
		} catch (OutOfMemoryError | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException ioexc) {
				ioexc.printStackTrace();
			}
		}

		return stringBuilder.toString();
	}

	public void closeConnection(){
		if (connection != null) {
			connection.disconnect();
			connection = null;
		}
	}

	public static boolean isNetworkinOnline (Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeConnection = cm.getActiveNetworkInfo();
		Boolean isOnline = (activeConnection != null) && activeConnection.isConnected();
		return isOnline;
	}

}
