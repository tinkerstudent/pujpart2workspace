package com.tinkeracademy.projects;

public class ChatClient {

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
		Window.addTextArea("<Enter Your Name>", 4, 10, true);
		Window.addButton("Send");
	}

}