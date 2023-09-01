package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {

	public static void main(String[] args) {

		Frame frame = new Frame();
		frame.setVisible(true);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frame.setSize(new Dimension(screenSize.width * 3 / 4, screenSize.height * 3 / 4));

	}
}
