package com.tinkeracademy.projects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

public class ChatService implements Runnable {

	public final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public ScheduledFuture<?> futureTask;
	
	public File chatFile;
	
	public String chatUser;
	
	public enum ChatStatus {
		YES,
		NO,
		ERROR
	}
	
	public void startService() {
		futureTask = scheduler.scheduleAtFixedRate(this, 5, 5, TimeUnit.SECONDS);
	}
	
	public void stopService() {
		futureTask.cancel(false);
	}
	
	public void run() {
		// https://hc.apache.org/downloads.cgi
		
	}
	
	public String getChatUser() {
		BufferedReader br = null;
		String content = null;
		try {		
			br = new BufferedReader(new FileReader(chatFile));
			content = br.readLine();
		} catch(IOException e) {
			System.out.println("Oops:"+e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("Oops:"+e);
				}
			}
		}
		return content;
	}
	
	public List<String> getChatFileContents() {
		BufferedReader br = null;
		List<String> chatLines = null;
		try {		
			br = new BufferedReader(new FileReader(chatFile));
			chatLines = IOUtils.readLines(br);
			chatLines.remove(0);
		} catch(IOException e) {
			System.out.println("Oops:"+e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("Oops:"+e);
				}
			}
		}
		return chatLines;
	}
	
	public ChatStatus initializeChatFile() {
		String userHomeDirectory = System.getProperty("user.home");
		try {		
			chatFile = new File(userHomeDirectory, "TinkerAcademyChat.tff");
			if (!chatFile.exists()) {
				chatFile.createNewFile();
				return ChatStatus.NO;
			} else if (chatFile.length() == 0) {
				return ChatStatus.NO;
			} else {
				chatUser = getChatUser();
			}
			return ChatStatus.YES;
		} catch(IOException e) {
			System.out.println("Oops:"+e);
			return ChatStatus.ERROR;
		}
	}
	
	public ChatStatus initializeChatUser(String userName) {
		ChatStatus chatStatus =  updateChatFileLine(userName);
		if (chatStatus == ChatStatus.YES) {
			chatUser = userName;
		}
		return chatStatus;
	}
	
	public ChatStatus postChatMessage(String msg) {
		return updateChatFileLine(chatUser + ": " + msg);
	}
	
	public ChatStatus updateChatFileLine(String line) {
		PrintWriter pw = null;
		try {		
			pw = new PrintWriter(new FileWriter(chatFile, true));
			pw.print(line);pw.print('\n');
			pw.flush();
			return ChatStatus.YES;
		} catch(IOException e) {
			System.out.println("Oops:"+e);
			return ChatStatus.ERROR;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
