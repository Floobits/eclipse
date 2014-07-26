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
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JSeparator;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


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
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[]{490, 0};
			gbl_contentPanel.rowHeights = new int[]{22, 22, 0};
			gbl_contentPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			contentPanel.setLayout(gbl_contentPanel);
		}
		String prompt = String.format("<html><p>The following %s different from your local version:</html></p>", conflicts.length == 1 ? "file is" : "files are");
		JLabel lblNewLabel = new JLabel(prompt);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setMinimumSize(new Dimension(-1, -1));
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 1;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				JList list = new JList();
//				list.setVisibleRowCount(-1);
				list.setListData(conflicts);
				scrollPane.setColumnHeaderView(list);
			}
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
