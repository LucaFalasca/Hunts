package logic.state.draw.states;

import javafx.scene.canvas.GraphicsContext;

public class OvalState implements State{

	private static OvalState INSTANCE = new OvalState();
	
	private OvalState() {
		
	}
	
	public static OvalState getInstance() {
		return INSTANCE;
	}
	
	@Override
	public boolean draw(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		double w = (endX - startX);
		double h = (endY- startY);
		
		gc.fillOval(startX, startY, w, h);
		return false;
	}
	
	

}
