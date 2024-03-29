package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.media.opengl.DebugGL2;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import display.Display;

public class GameCube extends GLCanvas implements GLEventListener,MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private int afstand;
	private int aanzicht;
	private int hoogte;
	private Display display;
	private FPSAnimator animator;
	private GLUgl2 glu;
	private int corX;
	private int corY;

	public GameCube(int width,int height,GLCapabilities capabilities) {
		super(capabilities);
		setSize(width,height);
		afstand = 280;
		aanzicht = 0;
		hoogte = 0;
		display = new Display();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		setCamera(gl,glu,68,68,68,afstand,aanzicht,hoogte);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int[][][] cube_red = display.getLedsRed();
		int[][][] cube_green = display.getLedsGreen();
		gl.glColor3f(0f,0f,255f);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE);
		gl.glPushMatrix();
		gl.glRotatef(aanzicht,0.0f,1.0f,0.0f);
		gl.glRotatef(-hoogte,1.0f,0.0f,0.0f);
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0.0f,0.0f);
		gl.glVertex3i(0,-3,-4);
		gl.glTexCoord2f(0.0f,0.0f);
		gl.glVertex3i(0,3,-4);
		gl.glTexCoord2f(0.0f,0.0f);
		gl.glVertex3i(4,3,-4);
		gl.glTexCoord2f(0.0f,0.0f);
		gl.glVertex3i(4,-3,-4);
		gl.glEnd();
		gl.glPopMatrix();
		for (int x = 0;x < 16;x++) {
			gl.glTranslatef(8f,0f,0f);
			for (int y = 0;y < 16;y++) {
				gl.glTranslatef(0f,8f,0f);
				for (int z = 0;z < 16;z++) {
					gl.glTranslatef(0f,0f,8f);
					try {
						if (cube_red[x][y][z] != 0 || cube_green[x][y][z] != 0) {
							gl.glColor3f(display.getLedsRed()[x][y][z] / 255f,display.getLedsGreen()[x][y][z] / 255f,0f);
							gl.glEnable(GL.GL_BLEND);
							gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE);
							// gl.glDepthMask(false);
							gl.glPushMatrix();
							gl.glRotatef(aanzicht,0.0f,1.0f,0.0f);
							gl.glRotatef(-hoogte,1.0f,0.0f,0.0f);
							gl.glBegin(GL2.GL_QUADS);
							gl.glTexCoord2f(0.0f,0.0f);
							gl.glVertex3i(0,-3,-4);
							gl.glTexCoord2f(0.0f,0.0f);
							gl.glVertex3i(0,3,-4);
							gl.glTexCoord2f(0.0f,0.0f);
							gl.glVertex3i(4,3,-4);
							gl.glTexCoord2f(0.0f,0.0f);
							gl.glVertex3i(4,-3,-4);
							gl.glEnd();
							gl.glPopMatrix();
						}
					}
					catch (NullPointerException e) {}
				}
				gl.glTranslatef(0f,0f,-128f);
			}
			gl.glTranslatef(0f,-128f,0f);
		}
		gl.glTranslatef(-128f,0f,0f);
		gl.glDepthMask(true);
		drawCubeSkeleton(gl);
		gl.glFlush();
	}

	private void drawCubeSkeleton(GL2 gl) {
		GLUT glut = new GLUT();
		gl.glColor3f(0.2f,0.2f,0.2f);
		gl.glLoadIdentity();
		// glu.gluLookAt(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		gl.glScalef(128.0f,128.0f,128.0f);
		gl.glTranslatef(0.525f,0.525f,0.525f);
		glut.glutWireCube(1f);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		drawable.setGL(new DebugGL2(gl));
		// Enable z-buffer
		gl.glEnable(GL.GL_DEPTH_TEST);
		// Enable blending
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL.GL_TEXTURE_2D);
		// Reset view
		gl.glLoadIdentity();
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT | GL.GL_COLOR_BUFFER_BIT);
		gl.glClearColor(0f,0f,0f,1f);
		// Start animator.
		try {
			animator = new FPSAnimator(this,60);
			animator.start();
		}
		catch (GLException e) {}
		glu = new GLUgl2();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0,0,width,height);
	}

	/**
	 * deze methode bepaaldt de waardes hoe naar een meegegeven punt X Y Z wordt
	 * gekeken, aan de hand van de afstand, aanzicht en hoogte. als niet aan de
	 * voorwaarde wordt voldaan wordt de camera op een standaard positie gezet
	 * 
	 * @param gl
	 * @param glu
	 * @param X
	 *        X coordinaat waar naar gekeken wordt
	 * @param Y
	 *        Y coordinaat waar naar gekeken wordt
	 * @param Z
	 *        Z coordinaat waar naar gekeken wordt
	 * @param afstand
	 *        de afstand tussen het oog en het punt waarnaar gekeken wordt,
	 *        deze waarde moet tussen 1 en 1000 zijn
	 * @param aanzicht
	 *        een waarde van 0 t/m 360 die de hoek waarnaar het object
	 *        gekeken wordt bepaaldt
	 * @param hoogte
	 *        een waarde van -89 t/m 89 die de hoogte van het oog bepaaldt
	 */
	private int[] setCamera(GL2 gl, GLUgl2 glu, int X, int Y, int Z, int afstand, int aanzicht, int hoogte) {
		// Change to projection matrix.
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		// Perspective.
		float widthHeightRatio = (float)getWidth() / (float)getHeight();
		glu.gluPerspective(45,widthHeightRatio,1,1000);
		// Calculate the point where the eye is
		float eyeX = 0,eyeY = 0,eyeZ = 30;
		eyeZ = (float)(Math.cos(Math.toRadians(aanzicht)) * afstand);
		eyeX = (float)(Math.sin(Math.toRadians(aanzicht)) * afstand);
		float h;
		if (hoogte < 0)
			h = hoogte * -1;
		else
			h = hoogte;
		eyeX *= ((90 - h) / 90);
		eyeZ *= ((90 - h) / 90);
		eyeY = (float)Math.sqrt((afstand * afstand) - ((eyeX * eyeX) + (eyeZ * eyeZ)));
		if (hoogte < 0)
			eyeY *= -1;
		eyeX += X;
		eyeY += Y;
		eyeZ += Z;
		if (afstand < 1 || afstand > 1000 || aanzicht < 0 || aanzicht > 360 || hoogte < -89 || hoogte > 89) {
			eyeX = X;
			eyeY = Y;
			eyeZ = Z + 30;
		}
		glu.gluLookAt(eyeX,eyeY,eyeZ,X,Y,Z,0,1,0);
		// Change back to model view matrix.
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
		int[] integer = new int[3];
		integer[0] = (int)eyeX;
		integer[1] = (int)eyeY;
		integer[2] = (int)eyeZ;
		return integer;
	}

	public int getAfstand() {
		return afstand;
	}

	public void setAfstand(int afstand) {
		this.afstand = afstand;
	}

	public int getAanzicht() {
		return aanzicht;
	}

	public void setAanzicht(int aanzicht) throws Exception {
		if (aanzicht < 0 || aanzicht > 359)
			throw new Exception("this value should be between 0 & 359");
		this.aanzicht = aanzicht;
	}

	public int getHoogte() {
		return hoogte;
	}

	public void setHoogte(int hoogte) throws Exception {
		if (hoogte < -89 || hoogte > 89)
			throw new Exception("this value should be between -89 & 89");
		this.hoogte = hoogte;
	}

	/**
	 * verandert het aanzicht van de cube
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getX() < corX)
			aanzicht += 5;
		else if (e.getX() > corX)
			aanzicht -= 5;
		if (e.getY() < corY)
			hoogte -= 5;
		else if (e.getY() > corY)
			hoogte += 5;
		if (aanzicht < 0)
			aanzicht = 359;
		else if (aanzicht > 359)
			aanzicht = 0;
		if (hoogte > 89)
			hoogte = 89;
		else if (hoogte < -89)
			hoogte = -89;
		corX = e.getX();
		corY = e.getY();
	}

	/**
	 * houdt de X en Y coordinaten bij
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		corX = e.getX();
		corY = e.getY();
	}

	/**
	 * methode om dit objct om te zetten naar een byte array
	 * 
	 * @return
	 */
	public byte[] save() {
		return display.toByte();
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}
}