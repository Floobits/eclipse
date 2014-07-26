package floobits;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JList;


public class ResolveConflicts extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4645947310732909361L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public ResolveConflicts(final String[] conflicts) {
		setBounds(100, 100, 500, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{	
		    String prompt = String.format("<html><p>The following %s different from your local version</html></p>", conflicts.length == 1 ? "file is" : "files are"); 
			JLabel lblNewLabel = new JLabel(prompt);
			contentPanel.add(lblNewLabel);
		}
		{
			JList list = new JList();
			list.setListData(conflicts);
			contentPanel.add(list);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnLocal = new JButton("Overwrite Local FIles");
				btnLocal.setActionCommand("local");
				buttonPane.add(btnLocal);
				getRootPane().setDefaultButton(btnLocal);
			}
			{
				JButton btnRemote = new JButton("Overwrite Remote Files");
				btnRemote.setActionCommand("remote");
				buttonPane.add(btnRemote);
			}
			{
				JButton cancelButton = new JButton("Disconnect");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
