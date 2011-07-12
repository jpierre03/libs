
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author jpierre03
 */
public class FirstApplet extends java.applet.Applet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public void init() {

		JPanel unPanel = new JPanel();
		final Label unLabel = new Label("Hello World");
		unLabel.setSize(100, 30);

		JButton unBouton = new JButton("mon bouton");
		unBouton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				//while(true){

				long current = System.currentTimeMillis();
//				current % 60*60

				//unLabel.setText(currentDate.toString() + " "+ current % 60*60);
				unLabel.setText(current % 60 + "");
				//	try {
				//		Thread.sleep(10 * 1000);
				//	} catch (InterruptedException ex) {
				//	}
			//	}
			}
		});
		unPanel.add(unBouton);
		unPanel.add(unLabel);
		this.add(unPanel);
	}
}
