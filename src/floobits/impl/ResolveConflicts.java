package floobits.impl;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class ResolveConflicts extends MessageDialog {

	public static enum RetCode { LOCAL, REMOTE, CANCEL};
	private String[] conflicts; 

	public ResolveConflicts(Shell shell, final String[] conflicts) {
		super(shell, "Resolve Conflicts", null, "The following local files are different.", MessageDialog.NONE, new String[]{"Overwrite Local Files", "Overwrite Remote Files", "Cancel"}, 2);
		this.conflicts = conflicts;
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		List list = new List(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		for (String cString : conflicts) {
			list.add(cString);
		}
		GridData gridData = new GridData();
	    gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalAlignment = GridData.FILL;
	    gridData.horizontalSpan = 2;
		list.setLayoutData(gridData);
		return list;
	}

	public RetCode getExitCode() {
		int open = super.open();
		switch (open) {
		case 2:
			return RetCode.CANCEL;
		case 1:
			return RetCode.REMOTE;
		case 0:
			return RetCode.LOCAL;
		}
		return RetCode.CANCEL;
	}
}
