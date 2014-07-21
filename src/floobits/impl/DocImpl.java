package floobits.impl;

import java.util.ArrayList;

import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;

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
	
	public DocImpl(IContext context, IDocumentExtension4 doc) {
		this.context = context;
		this.doc = doc;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return ((IDocument) doc).get();
	}

	@Override
	public void setText(String text) {
		lastModified = System.currentTimeMillis();
		doc.set(text, lastModified);
		
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean makeWritable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IFile getVirtualFile() {
		// TODO Auto-generated method stub
		return null;
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
        return text;
	}

}
