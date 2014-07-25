package floobits.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import floobits.Listener;
import floobits.common.EditorEventHandler;
import floobits.common.Ignore;
import floobits.common.RunLater;
import floobits.common.interfaces.IContext;
import floobits.common.protocol.FlooUser;
import floobits.utilities.Flog;

public class ContextImpl extends IContext {
	
	public IWorkspace workspace;
	private Listener listener = new Listener(this);

	public ContextImpl(IWorkspace workspace) throws CoreException {
		this.workspace = workspace;
		this.iFactory = new FactoryImpl(workspace, this);
	}

	@Override
	protected void shareProjectDialog(String name, List<String> orgs,
			String host, boolean _private_, String projectPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String selectAccount(String[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getActualContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadChatManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flashMessage(String message) {
		Flog.log("%s", message);
		
	}

	@Override
	public void warnMessage(String message) {
		Flog.warn(message);
		
	}

	@Override
	public void statusMessage(final String message) {
        Display.getDefault().syncExec(new Runnable() {
            public void run() {
            	Flog.log("%s", message);
//                IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(BatchView.ID);
//                window.getActivePage().findView(null).getgetViewSite().getActionBars().getStatusLineManager();
////                WorkbenchWindow w = (WorkbenchWindow) window
//                w.getStatusLineManager().setMessage(message);
            }
        });
	}

	@Override
	public void errorMessage(final String message) {
        Display.getDefault().syncExec(new Runnable() {
            public void run() {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openQuestion(shell, "Warning", message);
            }
        });
	}

	@Override
	public void chat(String username, String msg, Date messageDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openChat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listenToEditor(EditorEventHandler editorEventHandler) {
		listener.start(workspace, editorEventHandler);	
	}

	@Override
	public void setUsers(Map<Integer, FlooUser> users) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(boolean b) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mainThread(Runnable runnable) {
		Display.getDefault().asyncExec(runnable);
	}

	@Override
	public void readThread(Runnable runnable) {
		mainThread(runnable);
	}

	@Override
	public void writeThread(Runnable runnable) {
		mainThread(runnable);
	}

	@Override
	public void dialog(final String title, final String body, final RunLater<Boolean> runLater) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				boolean yes = MessageDialog.openQuestion(shell, title, body);
				runLater.run(yes);
			}
		});
	}

	@Override
	public void dialogDisconnect(int tooMuch, int howMany) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dialogPermsRequest(String username, RunLater<String> perms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean dialogTooBig(LinkedList<Ignore> tooBigIgnores) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dialogResolveConflicts(Runnable stompLocal,
			Runnable stompRemote, boolean readOnly, Runnable flee,
			String[] conflictedPathsArray) {
		// TODO Auto-generated method stub
		
	}

}
