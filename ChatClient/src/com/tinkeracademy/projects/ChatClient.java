package com.tinkeracademy.projects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

import com.tinkeracademy.projects.ChatService.ChatStatus;

public class ChatClient implements ActionListener {

	public JButton button;
	
	public JTextArea chatMessage;
	
	ChatService chatService;
	
	ChatStatus initStatus = ChatStatus.NO;
	
	public ChatClient() {
		chatService = new ChatService();
	}
	
	public static void main(String[] args) {
		ChatClient chatClient = new ChatClient();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatClient.createAndShowGUI();
			}
		});
	}

	public void createAndShowGUI() {
		Window.show();
		Window.addLabel("Chat Application Developed By: Tinker Academy v1.0");
		Window.addTextArea("", 13, 10, false);
		chatMessage = Window.addTextArea("<Enter Your Name>", 4, 10, true);
		button = Window.addButton("Send");
		button.addActionListener(this);
		initStatus = chatService.initializeChatFile();
		if (initStatus == ChatStatus.ERROR) {
			showError();
		} else if (initStatus == ChatStatus.YES) {
			updateChatHistory();
		} else if (initStatus == ChatStatus.NO) {
			promptChatUser();
		}
	}
	
	public void updateChatHistory() {
		clearChatMessage();
	}
	
	public void clearChatMessage() {
		chatMessage.setText("");
	}
	
	public void promptChatUser() {
		chatMessage.setText(getUserNamePrompt());
	}
	
	public String getUserNamePrompt() {
		String txt = "<Enter Your Name>";
		return txt;
	}
	
	public void showError() {
		chatMessage.setText(getErrorText());
	}
	
	public String getErrorText() {
		String txt = "Oops! Error! Call Super.., no wait, Call Bat.., no wait, just Call me!";
		return txt;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (initStatus == ChatStatus.NO) {
			String chatName = chatMessage.getText();
			System.out.println("Your name is " + chatName);
			ChatStatus chatUserStatus = chatService.initializeChatUser(chatName);
			if (chatUserStatus == ChatStatus.YES) {
				initStatus = ChatStatus.YES;
			}
		} else if (initStatus == ChatStatus.YES) {
			String chatText = chatMessage.getText();
			System.out.println(chatText);
			chatService.updateChatFileLine(chatText);
		}
		clearChatMessage();
	}
	
}
