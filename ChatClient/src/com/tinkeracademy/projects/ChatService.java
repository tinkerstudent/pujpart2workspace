package com.tinkeracademy.projects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatService {

	public File chatFile;
	
	public String chatUser;
	
	public enum ChatStatus {
		YES,
		NO,
		ERROR
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
			}
			return ChatStatus.YES;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return ChatStatus.ERROR;
	}
	
	public ChatStatus initializeChatUser(String userName) {
		ChatStatus chatStatus =  updateChatFileLine(userName);
		if (chatStatus == ChatStatus.YES) {
			chatUser = userName;
		}
		return chatStatus;
	}
	
	public ChatStatus updateChatFileLine(String line) {
		PrintWriter pw = null;
		try {		
			pw = new PrintWriter(new FileWriter(chatFile, true));
			pw.println(line);
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
