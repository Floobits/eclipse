package floobits.impl;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import floobits.common.interfaces.IDoc;
import floobits.common.interfaces.IFactory;
import floobits.utilities.Flog;


public class FactoryImpl implements IFactory{
	IFileSystem lfs;
	IWorkspace workspace;
	ContextImpl context;
	IWorkspaceRoot root;
	IFileStore store;
	Path path;
	
	public FactoryImpl(IWorkspace workspace, ContextImpl context) throws CoreException {
		this.workspace = workspace;
		this.context = context;
		lfs = EFS.getLocalFileSystem();
		root = workspace.getRoot();
		store = EFS.getStore(root.getLocationURI());
	}
	
	public IFileInfo getInfo(org.eclipse.core.resources.IFile file) {
		IFileStore _store = lfs.getStore(file.getLocationURI());
		if (_store == null) {
			return null;
		}
		return _store.fetchInfo();
	}
	
	public IFileStore getFileStore(URI uri) {
		return lfs.getStore(uri);
	}

	private org.eclipse.core.resources.IFile[] filterNonExistentFiles(org.eclipse.core.resources.IFile[] files) {
		if (files == null)
			return null;

		int length = files.length;
		ArrayList<IFile> existentFiles = new ArrayList<IFile>(length);
		for (org.eclipse.core.resources.IFile file : files) {
			if (file.exists()) existentFiles.add(file);
		}
		return existentFiles.toArray(new org.eclipse.core.resources.IFile[existentFiles.size()]);
	}
	
	public org.eclipse.core.resources.IFile getEFile(IFileStore fileStore) {
		org.eclipse.core.resources.IFile[] files = workspace.getRoot().findFilesForLocationURI(fileStore.toURI());
		files = filterNonExistentFiles(files);
		if (files == null || files.length == 0)
			return null;

		// WTF?
		return files[0];
	}
	
	@Override
	public floobits.common.interfaces.IFile findFileByIoFile(File file) {
		// TODO Auto-generated method stub
		IFileStore store = lfs.getStore(new Path(file.getPath()));
		if (store == null) {
			return null;
		}
		return new FileImpl(context, store);
	}

	@Override
	public IDoc getDocument(floobits.common.interfaces.IFile file) {
		org.eclipse.core.resources.IFile file2 = root.getFile(new Path(file.getPath()));
		Flog.log("%s", file2.getFullPath());
        try {
			return new DocImpl(context, file2);
		} catch (CoreException e) {
			Flog.warn(e);
			return null;
		}
	}

	@Override
	public IDoc getDocument(String relPath) {
		org.eclipse.core.resources.IFile file2 = root.getFile(new Path(relPath));
        try {
			return new DocImpl(context, file2);
		} catch (CoreException e) {
			Flog.warn(e);
			return null;
		}
	}

	@Override
	public floobits.common.interfaces.IFile createDirectories(String path) {
		Path path2 = new Path(path);
		IFileStore child = store.getFileStore(path2);
		try {
			child.mkdir(0, null);
		} catch (CoreException e) {
			Flog.warn(e);
			return null;
		}
		return new FileImpl(context, child);
	}

	@Override
	public floobits.common.interfaces.IFile findFileByPath(String path) {
		IPath path2 = new Path(path).makeRelativeTo(context.path);
		IFileStore child = store.getFileStore(path2);
		return new FileImpl(context, child);
	}

	@Override
	public void removeHighlightsForUser(int userID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHighlight(Integer userId, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean openFile(File f) {     
//		#TODO: probably needs to be relative
		IFile file = root.getFile(new Path(f.getPath()));
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			IDE.openEditor(page, file);
		} catch (PartInitException e) {
			Flog.warn(e);
			return false;
		}
		return true;
//		IProject project = root.getProject("External Files");
//		if (!project.exists())
//		    project.create(null);
//		if (!project.isOpen())
//		    project.open(null);
//		Shell shell = window.getShell();
//		String name = new FileDialog(shell, SWT.OPEN).open();
//		if (name == null)
//		    return false;
//		IPath location = new Path(name);
//		IFile file = project.getFile(location.lastSegment());
//		file.createLink(location, IResource.NONE, null);
//		IWorkbenchPage page = window.getActivePage();
//		if (page != null)
//		    page.openEditor(file);
//		
//		return true;
	}

	@Override
	public void clearHighlights() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearReadOnlyState() {
		// TODO Auto-generated method stub
		
	}

}
