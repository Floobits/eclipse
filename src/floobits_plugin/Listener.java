package floobits_plugin;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

//class EditorListener implements ISelectionListener, IFileBufferListener, IDocumentListener, IPartListener2 {
//    protected BytecodeOutlineView view;
//
//    EditorListener(BytecodeOutlineView view){
//        this.view = view;
//    }
//    /**
//     * @see org.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.IWorkbenchPartReference)
//     */
//    public void partOpened(IWorkbenchPartReference partRef) {
//        view.handlePartVisible(partRef.getPart(false));
//    }
    

public class Listener implements IResourceChangeListener, IPartListener2 {
	private IDocumentListener documentListener = new IDocumentListener() {

		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {
			Activator.log(String.format("%s, ", event));
			
		}

		@Override
		public void documentChanged(DocumentEvent event) {
			Activator.log(String.format("%s, ", event));	
		}
	};
	
	public void start(IWorkbenchWindow window, IWorkspace iWorkspace) {
		iWorkspace.addResourceChangeListener(this);
		window.getPartService().addPartListener(this);
		IWorkbenchPage[] pages = window.getPages();
		for (IWorkbenchPage page : pages) {
			IEditorReference[] editorReferences = page.getEditorReferences();
			for (IEditorReference editorReference : editorReferences) {
				IEditorPart editor = editorReference.getEditor(false);
				IDocument document = getDocument((IEditorPart)editor);
				if (document != null) document.addDocumentListener(documentListener);
			}
		}
	}
	
	public void stop(IWorkspace iWorkspace) throws Exception {
		iWorkspace.removeResourceChangeListener(this);
	}
	
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
//		if(event.getType() != IResourceChangeEvent.POST_CHANGE){
//			return;
//		}
		try {
			event.getDelta().accept(new IResourceDeltaVisitor(){

				@Override
				public boolean visit(IResourceDelta delta) throws CoreException {
			         switch (delta.getKind()) {
			         case IResourceDelta.ADDED :
			             // handle added resource
			             break;
			         case IResourceDelta.REMOVED :
			             // handle removed resource
			             break;
			         case IResourceDelta.CHANGED :
			             // handle changed resource
			             break;
			         }
			         delta.getResource();
//			         log(Status.INFO, String.format("%s %s %s", delta.getResource().getName(), delta.getKind(), delta.getFullPath()));
			     return true;
				}});
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		
		if (!(part instanceof IEditorPart)) {
			return;
		}
		IDocument document = getDocument((IEditorPart)part);
		if (document != null) document.removeDocumentListener(documentListener);	
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
		IWorkbenchPart part = partRef.getPart(false);
		
		if (!(part instanceof IEditorPart)) {
			return;
		}
		IDocument document = getDocument((IEditorPart)part);
		if (document != null) document.addDocumentListener(documentListener);
		
	}

	private IDocument getDocument(IEditorPart editor) {
		if (editor == null) return null;
		IDocumentProvider documentProvider = ((ITextEditor) editor.getAdapter(ITextEditor.class)).getDocumentProvider();
		return documentProvider.getDocument(editor.getEditorInput());
	}
	
	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

}