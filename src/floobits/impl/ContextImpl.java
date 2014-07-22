package floobits.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;

import floobits.common.EditorEventHandler;
import floobits.common.Ignore;
import floobits.common.RunLater;
import floobits.common.interfaces.IContext;
import floobits.common.protocol.FlooUser;

public class ContextImpl extends IContext {
	
	private IWorkspace workspace;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warnMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void statusMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorMessage(String message) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readThread(Runnable runnable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeThread(Runnable runnable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dialog(String title, String body, RunLater<Boolean> runLater) {
		// TODO Auto-generated method stub
		
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
