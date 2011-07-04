
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class WebPage{
	public static void main(String[] args) throws Exception {
		String url = null;
		if(args.length>0){
			url=args[0];
		}else{
			url = "http://www.yahoo.com";
		}

		JEditorPane editor = new JEditorPane(url);
		editor.setEditable(false);
		JScrollPane pane = new JScrollPane(editor);
		JFrame f = new JFrame("HTML Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(pane);
		f.setSize(800, 600);
		f.setVisible(true);
	}
}

