package com.randi.dyned3.view;

import com.randi.dyned3.view.manager.RegisterManager;

public class RegisterScreen extends BasicScreen {

	public RegisterScreen() {
		super("Register");
		add(new RegisterManager());
	}
}