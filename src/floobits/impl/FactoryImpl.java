package floobits.impl;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.IFileTree;
import org.eclipse.core.internal.localstore.FileSystemResourceManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;

import floobits.common.interfaces.IContext;
import floobits.common.interfaces.IDoc;
import floobits.common.interfaces.IFactory;
import floobits.utilities.Flog;


public class FactoryImpl implements IFactory{
	IFileSystem lfs;
	IWorkspace workspace;
	IContext context;
	public IFileTree tree;
	
	public FactoryImpl(IWorkspace workspace, IContext context) throws CoreException {
		this.workspace = workspace;
		this.context = context;
		lfs = EFS.getLocalFileSystem();
		IWorkspaceRoot root = workspace.getRoot();
		IFileStore store = EFS.getStore(root.getLocationURI());
		tree = lfs.fetchFileTree(store, null); 	
	}
	
	public IFileInfo getInfo(org.eclipse.core.resources.IFile file) {
		IFileStore store = lfs.getStore(file.getLocationURI());
		if (store == null) {
			return null;
		}
		return tree.getFileInfo(store);
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
		IFileStore localFile = lfs.fromLocalFile(file);
		org.eclipse.core.resources.IFile eFile = getEFile(localFile);
		return new FileImpl(context, eFile);
	}

	@Override
	public IDoc getDocument(floobits.common.interfaces.IFile file) {
		// TODO Auto-generated method stub
		org.eclipse.core.resources.IFile eFile = ((FileImpl) file).file;
        return new DocImpl(context, eFile);
	}

	@Override
	public IDoc getDocument(String relPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public floobits.common.interfaces.IFile createDirectories(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public floobits.common.interfaces.IFile findFileByPath(String path) {
		// TODO Auto-generated method stub
		return null;
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
		Path ipath = new Path(f.getAbsolutePath());
		IFileStore fileLocation = EFS.getLocalFileSystem().getStore(ipath);
		FileStoreEditorInput fileStoreEditorInput = new FileStoreEditorInput(
		                            fileLocation);
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		                            .getActivePage();
		page.openEditor(fileStoreEditorInput, FormEditor.ID);
		return false;
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
