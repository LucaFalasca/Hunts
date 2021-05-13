package logic.state.draw;

import javafx.scene.canvas.GraphicsContext;
import logic.state.draw.states.State;

public class DrawMachine {
private State state;
	
	public DrawMachine(State state) {
		this.state = state;
	}
	
	public boolean draw(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		return state.draw(gc, startX, startY, endX, endY);
	}
	
	public void clean(GraphicsContext gc) {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return state.toString();
	}
}
