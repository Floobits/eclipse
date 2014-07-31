package floobits.impl;

import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProviderExtension;

import floobits.common.Constants;
import floobits.common.dmp.FlooPatchPosition;
import floobits.common.interfaces.IContext;
import floobits.common.interfaces.IDoc;
import floobits.common.interfaces.IFile;
import floobits.utilities.Flog;

public class DocImpl extends IDoc {
	
	private IContext context;
	private IDocumentExtension4 doc;
	public long lastModified = 0;
	private IDocumentProvider provider;
	private org.eclipse.core.resources.IFile eFile;
	
	public DocImpl(IContext context, org.eclipse.core.resources.IFile eFile) throws CoreException {
		this.context = context;
		this.eFile = eFile;
	}
	
	private boolean connect() {
		if (doc != null) {
			return true;
		}
		if (!eFile.exists()) {
			try {
				IPath fullPath = eFile.getParent().getFullPath();
				String relPath = context.toProjectRelPath(fullPath.toString());
				context.iFactory.createDirectories(relPath);
				eFile.getParent().refreshLocal(1, null);
				eFile.refreshLocal(0, null);
				eFile.create(null, 0, null);
			} catch (Throwable e) {
				Flog.warn(e);
			}
		}
		provider = new TextFileDocumentProvider();
		try {
			provider.connect(eFile);
		} catch (CoreException e) {
			Flog.warn(e);
			return false;
		}
        doc = (IDocumentExtension4) provider.getDocument(eFile);
        return true;
	}
	
	public void cleanup() {
		if (provider != null) {
			provider.disconnect(eFile);
		} 
		provider = null;
	}

	
	@Override
	public void removeHighlight(Integer userId, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyHighlight(String path, int userID, String username,
			Boolean stalking, Boolean force,
			ArrayList<ArrayList<Integer>> ranges) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		if (!connect()) {
			return;
		}
		try {
			provider.saveDocument(null, eFile, (IDocument) doc, true);
		} catch (CoreException e) {
			Flog.warn(e);
		}
		cleanup();
	}

	@Override
	public String getText() {
		if (!connect()) {
			return "";
		}
		String text = ((IDocument) doc).get();
		cleanup();
		return text;
	}

	@Override
	public void setText(String text) {
		if (!connect()) {
			return;
		}
		lastModified = System.currentTimeMillis();
		doc.set(text, lastModified);
		cleanup();
		
	}

	@Override
	public void setReadOnly(boolean readOnly) {
	}

	@Override
	public boolean makeWritable() {
		connect();
		((IDocumentProviderExtension)provider).setCanSaveDocument(eFile);
		cleanup();
		return true;
	}

	@Override
	public IFile getVirtualFile() {
		IFileStore store;
		try {
			store = EFS.getStore(eFile.getLocationURI());
		} catch (CoreException e) {
			Flog.warn(e);
			return null;
		}
		return new FileImpl(context, store);
	}

	@Override
	public String patch(FlooPatchPosition[] positions) {
		// TODO Auto-generated method stub
//        final Editor[] editors = EditorFactory.getInstance().getEditors(document, context.project);
//        final HashMap<ScrollingModel, Integer[]> original = new HashMap<ScrollingModel, Integer[]>();
//        for (Editor editor : editors) {
//            if (editor.isDisposed()) {
//                continue;
//            }
//            ScrollingModel scrollingModel = editor.getScrollingModel();
//            original.put(scrollingModel, new Integer[]{scrollingModel.getHorizontalScrollOffset(), scrollingModel.getVerticalScrollOffset()});
//        }
		connect();
        IDocument document = (IDocument) doc;
        DocumentRewriteSession rewriteSession = doc.startRewriteSession(DocumentRewriteSessionType.UNRESTRICTED);
        lastModified = System.currentTimeMillis();
        for (FlooPatchPosition flooPatchPosition : positions) {
            final int start = Math.max(0, flooPatchPosition.start);
            int end_ld = Math.max(start + flooPatchPosition.end, start);
            end_ld = Math.min(end_ld, document.getLength());
            final String contents = Constants.NEW_LINE.matcher(flooPatchPosition.text).replaceAll("\n");
            final int finalEnd_ld = end_ld;
            synchronized (context) {
                try {
                    context.setListener(false);
                    doc.replace(start, finalEnd_ld, contents, lastModified);
                } catch (Throwable e) {
                    Flog.warn(e);
                } finally {
                    context.setListener(true);
                }
            }
        }
        doc.stopRewriteSession(rewriteSession);
        String text = document.get();
//        for (Map.Entry<ScrollingModel, Integer[]> entry : original.entrySet()) {
//            ScrollingModel model = entry.getKey();
//            Integer[] offsets = entry.getValue();
//            model.scrollHorizontally(offsets[0]);
//            model.scrollVertically(offsets[1]);
//        }
        cleanup();
        return text;
	}

}
