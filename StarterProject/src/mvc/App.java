package mvc;

public class App {
	public static void main(String[] args) {
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);
		frame.getView().setModel(model);
		
		frame.setVisible(true);
	}
}
