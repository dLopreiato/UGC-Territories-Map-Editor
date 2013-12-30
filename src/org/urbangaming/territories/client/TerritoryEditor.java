package org.urbangaming.territories.client;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 * This is a JComponent that can edit polygons on top of an image.
 * @author Andrew Lopreiato
 * @version 1.0 12/30/2013
 */
public class TerritoryEditor extends JComponent implements MouseListener, MouseMotionListener {
	// DATA MEMBERS
	private BufferedImage BackgroundMap_;
	private Polygon EdittingPolygon_; 
	private ActionListener CursorOver_;
	private ActionListener CursorDefault_;
	private Integer HoveringPoint_;
	private static final int VERTEX_RADIUS = 3;
	private static final long serialVersionUID = 1L;
	// END DATA MEMBERS
	
	public TerritoryEditor(BufferedImage bufferedImage, ActionListener mouseDefault, ActionListener mouseOver) {
		SetBackgroundMap(bufferedImage);
		SetPolygon(null);
		CursorOver_ = mouseOver;
		CursorDefault_ = mouseDefault;
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public TerritoryEditor(BufferedImage bufferedImage,Polygon poly,
			ActionListener mouseDefault, ActionListener mouseOver) {
		
		SetBackgroundMap(bufferedImage);
		SetPolygon(poly);
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
		
		if (EdittingPolygon_ == null)
			return;
		
		g2.setColor(Color.GRAY);
		g2.fillPolygon(EdittingPolygon_);
		
		g2.setColor(Color.BLACK);
		for (int i = 0; i < EdittingPolygon_.npoints; i++) {
			g2.fillOval(EdittingPolygon_.xpoints[i] - VERTEX_RADIUS,
					EdittingPolygon_.ypoints[i] - VERTEX_RADIUS,
					VERTEX_RADIUS * 2,
					VERTEX_RADIUS * 2);
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if (EdittingPolygon_ == null)
			return;
		if (HoveringPoint_ != null) {
			EdittingPolygon_.xpoints[HoveringPoint_] = me.getX();
			EdittingPolygon_.ypoints[HoveringPoint_] = me.getY();
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		if (EdittingPolygon_ == null)
			return;
		for (int i = 0; i < EdittingPolygon_.npoints; i++) {
			if (WithinRadius(me.getX(), me.getY(),
					EdittingPolygon_.xpoints[i], EdittingPolygon_.ypoints[i], VERTEX_RADIUS)) {
				if (HoveringPoint_ == null) {
					CursorOver_.actionPerformed(new ActionEvent(this, 1, "Mouseover"));
					HoveringPoint_ = i;
				}
				return;
			}
		}
		if (HoveringPoint_ != null) {
			CursorDefault_.actionPerformed(new ActionEvent(this, 2, "Mouseout"));
			HoveringPoint_ = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if (EdittingPolygon_ == null)
			return;
		if (me.getButton() == MouseEvent.BUTTON3 && HoveringPoint_ == null) {
			// add a point
			EdittingPolygon_.addPoint(me.getX(), me.getY());
			repaint();
		}
		if (me.isControlDown() && me.getButton() == MouseEvent.BUTTON1 && HoveringPoint_ != null) {
			// delete a point
			Polygon newPolygon = new Polygon();
			for (int i = 0; i < EdittingPolygon_.npoints; i++) {
				if (i != HoveringPoint_) {
					newPolygon.addPoint(EdittingPolygon_.xpoints[i], EdittingPolygon_.ypoints[i]);
				}
			}
			SetPolygon(newPolygon);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
	
	public void SetBackgroundMap(BufferedImage image) {
		BackgroundMap_ = image;
		this.setSize(BackgroundMap_.getWidth(), BackgroundMap_.getWidth());
	}
	
	public void SetPolygon(Polygon p) {
		if (p != null) {
			EdittingPolygon_ = new Polygon();
			for (int i = 0; i < p.npoints; i++) {
				EdittingPolygon_.addPoint(p.xpoints[i], p.ypoints[i]);
			}
		} else {
			EdittingPolygon_ = null;
		}
		HoveringPoint_ = null;
		repaint();
	}
	
	public Polygon GetPolygon() {
		return EdittingPolygon_;
	}
	
	private boolean WithinRadius(int x1, int y1, int x2, int y2, double distance) {
		return (Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow(y2 - y1, 2))) <= distance;
	}

}
