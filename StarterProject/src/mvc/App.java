package mvc;

import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf;

public class App {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);
		frame.getView().setModel(model);
		
		frame.setVisible(true);
	}
}
