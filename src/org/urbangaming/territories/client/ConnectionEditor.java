package org.urbangaming.territories.client;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import org.urbangaming.territories.core.ConnectionLine;

/**
 * A JComponent that works as an editor for ConnectionLines.
 * @author Andrew Lopreiato
 * @version 1.0 1/5/2014
 */
public class ConnectionEditor extends JComponent implements MouseListener, MouseMotionListener {
	
	// DATA MEMBERS
	private BufferedImage BackgroundMap_;
	private ConnectionLine EdittingLine_; 
	private ActionListener CursorOver_;
	private ActionListener CursorDefault_;
	private Boolean HoveringOne_;
	private static final int VERTEX_RADIUS = 3;
	private static final float REACTIVE_LENGTH = 15;
	private static final int REACTIVE_RADIUS = 5;
	private static final long serialVersionUID = 1L;
	// END DATA MEMBERS
	
	public ConnectionEditor(BufferedImage bufferedImage, ActionListener mouseDefault, ActionListener mouseOver) {
		SetBackgroundMap(bufferedImage);
		SetConnection(null);
		CursorOver_ = mouseOver;
		CursorDefault_ = mouseDefault;
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		if (BackgroundMap_ == null)
			return;
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(BackgroundMap_, null, 0, 0);
		
		if (EdittingLine_ == null)
			return;
		
		BasicStroke territoryOutline = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
		g2.setStroke(territoryOutline);
		if (EdittingLine_.Wrapping) {
			g2.setColor(Color.GRAY);
			// find slope of the lines
			
			double deltaX = (double)(EdittingLine_.X2 - EdittingLine_.X1);
			double deltaY = (double)(EdittingLine_.Y2 - EdittingLine_.Y1);
			
			double reactiveX = (REACTIVE_LENGTH * deltaX) / Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
			double reactiveY = (REACTIVE_LENGTH * deltaY) / Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
			
			g2.drawLine(EdittingLine_.X1, EdittingLine_.Y1,
					EdittingLine_.X1 - (int)reactiveX, EdittingLine_.Y1 - (int)reactiveY);
			g2.drawLine(EdittingLine_.X2, EdittingLine_.Y2,
					EdittingLine_.X2 + (int)reactiveX, EdittingLine_.Y2 + (int)reactiveY);
			
			g2.setColor(Color.BLACK);
			g2.drawOval(EdittingLine_.X1 - (int)reactiveX - REACTIVE_RADIUS,
					EdittingLine_.Y1 - (int)reactiveY - REACTIVE_RADIUS,
					REACTIVE_RADIUS * 2, REACTIVE_RADIUS * 2);
			g2.drawOval(EdittingLine_.X2 + (int)reactiveX - REACTIVE_RADIUS,
					EdittingLine_.Y2 + (int)reactiveY - REACTIVE_RADIUS,
					REACTIVE_RADIUS * 2, REACTIVE_RADIUS * 2);
		} else {
			g2.setColor(Color.GRAY);
			g2.drawLine(EdittingLine_.X1, EdittingLine_.Y1, EdittingLine_.X2, EdittingLine_.Y2);
		}
		
		g2.setColor(Color.BLACK);
		g2.fillOval(EdittingLine_.X1 - VERTEX_RADIUS, EdittingLine_.Y1 - VERTEX_RADIUS,
				VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
		g2.fillOval(EdittingLine_.X2 - VERTEX_RADIUS, EdittingLine_.Y2 - VERTEX_RADIUS,
				VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if (EdittingLine_ == null)
			return;
		if (HoveringOne_ != null) {
			if (HoveringOne_) {
				EdittingLine_.X1 = me.getX();
				EdittingLine_.Y1 = me.getY();
			} else {
				EdittingLine_.X2 = me.getX();
				EdittingLine_.Y2 = me.getY();
			}
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		if (EdittingLine_ == null)
			return;
		
		if (WithinRadius(me.getX(), me.getY(), EdittingLine_.X1, EdittingLine_.Y1, VERTEX_RADIUS)) {
			if (HoveringOne_ == null) {
				CursorOver_.actionPerformed(new ActionEvent(this, 1, "Mouseover"));
				HoveringOne_ = true;
			}
			return;
		}
		if (WithinRadius(me.getX(), me.getY(), EdittingLine_.X2, EdittingLine_.Y2, VERTEX_RADIUS)) {
			if (HoveringOne_ == null) {
				CursorOver_.actionPerformed(new ActionEvent(this, 1, "Mouseover"));
				HoveringOne_ = false;
			}
			return;
		}
		
		if (HoveringOne_ != null) {
			CursorDefault_.actionPerformed(new ActionEvent(this, 2, "Mouseout"));
			HoveringOne_ = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}
	
	public void SetBackgroundMap(BufferedImage image) {
		BackgroundMap_ = image;
		this.setSize(BackgroundMap_.getWidth(), BackgroundMap_.getWidth());
	}
	
	public void SetConnection(ConnectionLine cLine) {
		if (cLine != null) {
			EdittingLine_ = new ConnectionLine(cLine.X1, cLine.Y1, cLine.X2, cLine.Y2, cLine.Wrapping);
		} else {
			EdittingLine_ = null;
		}
		HoveringOne_ = null;
		repaint();
	}
	
	public ConnectionLine GetConnection() {
		return EdittingLine_;
	}
	
	private boolean WithinRadius(int x1, int y1, int x2, int y2, double distance) {
		return (Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow(y2 - y1, 2))) <= distance;
	}
	
	public void ToggleWrapping() {
		EdittingLine_.Wrapping = !(EdittingLine_.Wrapping);
		repaint();
	}

}
