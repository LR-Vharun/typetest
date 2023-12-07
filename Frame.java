

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener, KeyListener {
	String passage = ""; // Passage we get as sample to type
	String typedPass = ""; // Passage we type in the application
	String message = ""; // Messages to display at the end of the TypingTest

	int typed = 0; // to stores till which character the user has typed
	int count = 0;
	int total =0;
	int WPM,CPM,CPW,Acuracy=90;

	double start;
	double end;
	double elapsed;
	double seconds;

	boolean running; // when the person is typing
	boolean ended; // When the typing test has ended

	final int SCREEN_WIDTH;
	final int SCREEN_HEIGHT;
	final int DELAY = 10;

	JButton button;
	Timer timer;
	JLabel label;


	public Frame() {
		setBackground(new Color(129, 226, 237));
		setForeground(new Color(129, 226, 237));
		getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SCREEN_WIDTH = 720;
		SCREEN_HEIGHT = 400;
		this.setSize(789, 434);
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		button = new JButton("Lets Start typing!");
		button.setBackground(new Color(129, 226, 237));
		button.setFont(new Font("Arial Black", Font.BOLD, 30));
		button.setForeground(Color.BLACK);
		button.setVisible(true);
		button.addActionListener(this);
		button.setFocusable(false);

		getContentPane().add(button, BorderLayout.SOUTH);
		this.getContentPane().setBackground(Color.BLACK);
		
		
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setResizable(false);
		this.setTitle("Typing speed tester");
		this.revalidate();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);
	}

	public void draw(Graphics g) {
		g.setFont(new Font("Times New Roman", Font.BOLD, 25));

		if (running)

		{

			// To display the passage on the screen
			if (passage.length() > 1) {
				g.drawString(passage.substring(0, 50), g.getFont().getSize(), g.getFont().getSize() * 5);
				g.drawString(passage.substring(50, 100), g.getFont().getSize(), g.getFont().getSize() * 7);
				g.drawString(passage.substring(100, 150), g.getFont().getSize(), g.getFont().getSize() * 9);
				g.drawString(passage.substring(150, 200), g.getFont().getSize(), g.getFont().getSize() * 11);
			}

			// To displaying correctly typed passage in GREEN
			g.setColor(Color.GREEN);
			if (typedPass.length() > 0) {
				// This is if the typedPassages length is greater than 0 and less than 50
				if (typed < 50) // if the typed index is in the first line
					// From the first letter to the currently typed on, in green
					g.drawString(typedPass.substring(0, typed), g.getFont().getSize(), g.getFont().getSize() * 5); 
				else
					//If the typed character exceeds 50 we can directly show the whole line in green
					g.drawString(typedPass.substring(0, 50), g.getFont().getSize(), g.getFont().getSize() * 5); 
			}
			if (typedPass.length() > 50) {
				if (typed < 100)
					g.drawString(typedPass.substring(50, typed), g.getFont().getSize(), g.getFont().getSize() * 7);
				else
					g.drawString(typedPass.substring(50, 100), g.getFont().getSize(), g.getFont().getSize() * 7);
			}
			if (typedPass.length() > 100) {
				if (typed < 150)
					g.drawString(typedPass.substring(100, typed), g.getFont().getSize(), g.getFont().getSize() * 9);
				else
					g.drawString(typedPass.substring(100, 150), g.getFont().getSize(), g.getFont().getSize() * 9);
			}
			if (typedPass.length() > 150)
				g.drawString(typedPass.substring(150, typed), g.getFont().getSize(), g.getFont().getSize() * 11);
			running = false; // Once we have made the line green we can make running false so that it does
								// not keep repainting
			// Since when we type again running will become true and it will make the
			// substring from the start of line to next character green
		}
		if (ended) {
			if (WPM <= 40) {
				message = "You are an Average Typist";
				
			}
			else if (WPM > 40 && WPM <= 60) {
				message = "You are a Good Typist";
				
			}
			else if (WPM > 60 && WPM <= 100) {
				message = "You are an Excellent Typist";
				labelimg3.setVisible(true);
			}
			else {
				message = "You are an Elite Typist";
				
			}
			FontMetrics metrics = getFontMetrics(g.getFont());
			int lineHeight = g.getFontMetrics().getHeight();
			int y = g.getFont().getSize() * 9;
			g.setColor(Color.BLUE);
			g.drawString("Typing Test Completed!", (SCREEN_WIDTH - metrics.stringWidth("Typing Test Completed!")) / 2,g.getFont().getSize() * 6);

			g.setColor(Color.pink);
			g.drawString(message, (SCREEN_WIDTH - metrics.stringWidth(message)) / 2,g.getFont().getSize() * 13);
			g.drawString("Typing Speed: " + WPM + " Words Per Minute",(SCREEN_WIDTH - metrics.stringWidth("Typing Speed: " + WPM + " Words Per Minute")) / 2,g.getFont().getSize() * 9);
			y += lineHeight;
			g.drawString(" Characters Per Minute :" + CPM + " CPM",(SCREEN_WIDTH - metrics.stringWidth("Typing Speed: " + CPM + " Words Per Minute")) / 2,y);
			y += lineHeight;
			g.drawString(" You have :" + Acuracy + "% Acuracy",(SCREEN_WIDTH - metrics.stringWidth("Typing Speed: " + CPM + " Words Per Minute")) / 2,y);
			
//			g.drawString(message, (SCREEN_WIDTH - metrics.stringWidth(message)) / 2, y);

			timer.stop();
			ended = false;
			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (passage.length() > 1) {
			
			if (count == 0)
				start = LocalTime.now().toNanoOfDay();
				
			else if (count == 200) // Once all 200 characters are typed we will end the time and calculate time
									// elapsed
			{
				end = LocalTime.now().toNanoOfDay();
				elapsed = end - start;
				seconds = elapsed / 1000000000.0; // nano to seconds
				WPM = (int) (((200.0 / 5) / seconds) * 60); // number of character by 5 is one word by seconds is words
															// per second * 60 WPM
				CPW=(5);
				CPM=(int)((200.0/WPM)*CPW);
				
				ended = true;
				running = false;
				count++;
			}
			char[] pass = passage.toCharArray();
			
			if (typed < 200) {
				running = true;
				
				if (e.getKeyChar() == pass[typed]) {
					typedPass = typedPass + pass[typed]; // To the typed Passage we are adding what is currently typed
					typed++;
					count++;
					
				} // If the typed character is not equal to the current position it will not add
					// it to the typedPassage, so the user needs to type the right thing
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {		

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == button) 
		{
			
			button.setText("Try Again");
			passage = getPassage();
			timer = new Timer(DELAY, this);
			timer.start();
			running = true;
			ended = false;

			typedPass = "";
			message = "";

			typed = 0;
			count = 0;
			total = 0;
		}
		if (running)
			repaint();
		if (ended)
			repaint();
	}

	public static String getPassage() {
		ArrayList<String> Passages = new ArrayList<String>();
		String pas1 = "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he could se.";
		String pas2 = "The quick, brown fox jumps over a lazy dog. DJs flock by when MTV ax quiz prog. Junk MTV quiz graced by fox whelps. Bawds jog, flick quartz, vex nymphs. Waltz, bad nymph, for quick jigs vex! Fox nymph";
		String pas3 = "Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pro";
		String pas4 = "The European languages are members of the same family. Their separate existence is a myth. For science, music, sport, etc, Europe uses the same vocabulary. The languages only differ in their grammar,.";
		String pas5 = "A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was.";
		String pas6 = "Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large.";

		Passages.add(pas1);
		Passages.add(pas2);
		Passages.add(pas3);
		Passages.add(pas4);
		Passages.add(pas5);
		Passages.add(pas6);

		Random rand = new Random();
		int place = (rand.nextInt(6));

		String toReturn = Passages.get(place).substring(0, 200);
		if (toReturn.charAt(199) == 32) {
			toReturn = toReturn.strip();
			toReturn = toReturn + "."; // Adding a full stop at the last instead of a space
		}
		return (toReturn);
	}
}


