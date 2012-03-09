import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* g_window.java
 *
 * Hier wordt het venster ge�nitialiseerd dat voor heel het programma
 * gebruikt zal worden.
 * 
 */
public class g_window implements MouseMotionListener, MouseWheelListener
{
	JFrame frame = new JFrame("3D LED Cube Simulator/Editor " + s_version.getVersion());
	private MyJoglCanvas jogl;
	private int corY;
	private int corX;
	
	public void createWindow()
	{		
		frame.setLocation(200, 50);
		frame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			}
		});
		frame.setSize(new Dimension(720, 600));
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GLCapabilities capabilities = new GLCapabilities(null);
		capabilities.setRedBits(8);
		capabilities.setBlueBits(8);
		capabilities.setGreenBits(8);
		capabilities.setAlphaBits(8);
		jogl = new MyJoglCanvas(420, 420, capabilities);
		jogl.addGLEventListener(jogl);
		jogl.setFocusable(true);
		jogl.addMouseMotionListener(this);
		jogl.addMouseWheelListener(this);

		JPanel jpanel1 = new JPanel();
		jpanel1.setLayout(new BoxLayout(jpanel1, BoxLayout.LINE_AXIS));
		JPanel jpanel2 = new JPanel();
		JPanel jpanel3 = new JPanel();
		JPanel jpanel4 = new JPanel();

		jpanel2.add(jogl);

		jpanel1.add(jpanel2);
		jpanel1.add(jpanel3);
		
		frame.add(jpanel1);
		frame.add(jpanel4);
	}

	public void updateFrame() 
	{
		frame.repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int afstand = jogl.getAfstand();
		e.getWheelRotation();
		if(e.getWheelRotation()>0) afstand+=2;
		else if(e.getWheelRotation()<0) afstand-=2;
		jogl.setAfstand(afstand);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int aanzicht = jogl.getAanzicht();
		int hoogte = jogl.getHoogte();

		if(e.getX()<corX) aanzicht++;
		else if(e.getX()>corX) aanzicht--;

		if(e.getY()<corY) hoogte--;
		else if(e.getY()>corY) hoogte++;

		if(aanzicht<0) aanzicht = 360;
		else if(aanzicht>360) aanzicht = 0;

		if(hoogte>89) hoogte = 89;
		else if(hoogte<-89) hoogte = -89;

		jogl.setAanzicht(aanzicht);
		jogl.setHoogte(hoogte);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		corX = e.getX();
		corY = e.getY();
	}
}