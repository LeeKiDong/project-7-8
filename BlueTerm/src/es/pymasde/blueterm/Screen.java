package es.pymasde.blueterm;
/**
 * An abstract screen interface. A terminal screen stores lines of text. (The
 * reason to abstract it is to allow different implementations, and to hide
 * implementation details from clients.)
 */
interface Screen {
	/**
	 * Set line wrap flag for a given row. Affects how lines are logically wrapped
	 * when changing screen size or converting to a transcript.
	 */
	void setLineWrap(int row);

	/**
	 * Store byte b into the screen at location (x, y)
	 * 
	 * @param x
	 *        X coordinate (also known as column)
	 * @param y
	 *        Y coordinate (also known as row)
	 * @param b
	 *        ASCII character to store
	 * @param foreColor
	 *        the foreground color
	 * @param backColor
	 *        the background color
	 */
	void set(int x, int y, byte b, int foreColor, int backColor);

	/**
	 * Scroll the screen down one line. To scroll the whole screen of a 24 line
	 * screen, the arguments would be (0, 24).
	 * 
	 * @param topMargin
	 *        First line that is scrolled.
	 * @param bottomMargin
	 *        One line after the last line that is scrolled.
	 */
	void scroll(int topMargin, int bottomMargin, int foreColor, int backColor);

	/**
	 * Block copy characters from one position in the screen to another. The two
	 * positions can overlap. All characters of the source and destination must be
	 * within the bounds of the screen, or else an InvalidParemeterException will be
	 * thrown.
	 * 
	 * @param sx
	 *        source X coordinate
	 * @param sy
	 *        source Y coordinate
	 * @param w
	 *        width
	 * @param h
	 *        height
	 * @param dx
	 *        destination X coordinate
	 * @param dy
	 *        destination Y coordinate
	 */
	void blockCopy(int sx, int sy, int w, int h, int dx, int dy);

	/**
	 * Block set characters. All characters must be within the bounds of the screen,
	 * or else and InvalidParemeterException will be thrown. Typically this is
	 * called with a "val" argument of 32 to clear a block of characters.
	 * 
	 * @param sx
	 *        source X
	 * @param sy
	 *        source Y
	 * @param w
	 *        width
	 * @param h
	 *        height
	 * @param val
	 *        value to set.
	 * @param foreColor
	 *        the foreground color
	 * @param backColor
	 *        the background color
	 */
	void blockSet(int sx, int sy, int w, int h, int val, int foreColor, int backColor);

	/**
	 * Get the contents of the transcript buffer as a text string.
	 * 
	 * @return the contents of the transcript buffer.
	 */
	String getTranscriptText();

	/**
	 * Resize the screen
	 * 
	 * @param columns
	 * @param rows
	 */
	void resize(int columns, int rows, int foreColor, int backColor);
}