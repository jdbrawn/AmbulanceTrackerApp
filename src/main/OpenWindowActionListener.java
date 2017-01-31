package main;

import java.awt.event.*;

public class OpenWindowActionListener implements ActionListener {
	private AppWindow _old;
	private AppWindow _new;
	
	public OpenWindowActionListener(AppWindow oldWindow, AppWindow newWindow) {
		_old = oldWindow;
		_new = newWindow;
	}
	
	public void actionPerformed(ActionEvent e) {
		_new.show();
		_old.hide();
	}
}
