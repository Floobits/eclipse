package floobits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import floobits.common.EditorEventHandler;
import floobits.impl.ContextImpl;
import floobits.utilities.Flog;


public class Listener {
	private ContextImpl context;
	public EditorEventHandler editorEventHandler;
	public final AtomicBoolean isListening = new AtomicBoolean(false);
	protected final HashMap<IEditorPart,DocumentListenerManager> map = new HashMap<IEditorPart, DocumentListenerManager>();
	private Listener self;
	public Listener(ContextImpl context) {
		this.context = context;
		this.self = this;
	}
	
	private class DocumentListenerManager {
		private Listener listener;
		IEditorPart editor;
		public DocumentListenerManager(Listener listener, IEditorPart editor) {
			this.editor = editor;
			this.listener = listener;
		}
		
		private IPostSelectionProvider getProvider() {
			ITextEditor textEditor = (ITextEditor) editor.getAdapter(ITextEditor.class);
			ISelectionProvider selectionProvider = textEditor.getSelectionProvider();
			if (selectionProvider instanceof IPostSelectionProvider) {
				return (IPostSelectionProvider) selectionProvider;
			}
			return null;
		}
		
		private String getPath() {
			return ((FileEditorInput)editor.getEditorInput()).getPath().toString();
		}
		
		public void start() {
			
			if (!(editor.getEditorInput() instanceof FileEditorInput)) {
				return;
			}
			
			IDocument document = getDocument(editor);
			if (document == null) {
				return;
			}
			document.addDocumentListener(documentListener);
			IPostSelectionProvider selectionProvider = getProvider();
			if (selectionProvider != null) {
				selectionProvider.addPostSelectionChangedListener(selectionListener);
			}
			map.put(editor, this);
		}
		private IDocumentListener documentListener = new IDocumentListener() {

			@Override
			public void documentAboutToBeChanged(DocumentEvent event) {
				Flog.log(String.format("%s, ", event));
				
			}

			@Override
			public void documentChanged(DocumentEvent event) {
				Flog.log(String.format("%s, ", event));	
			}
		};
		
		private ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				if (listener.isListening.get()) {
					return;
				}
				if (!(event.getSelection() instanceof ITextSelection)) {
					return;
				}
				final ITextSelection textSelection = (ITextSelection) event.getSelection();
				event.getSource();
		        ArrayList<ArrayList<Integer>> range = new ArrayList<ArrayList<Integer>>();
		        int offset = textSelection.getOffset();
		        range.add(new ArrayList<Integer>(Arrays.asList(offset, offset+textSelection.getLength())));
				listener.editorEventHandler.changeSelection(getPath(), range, false);
			}
		};
		
		public void stop() {
			IDocument doc = getDocument(editor);
			doc.removeDocumentListener(documentListener);
			IPostSelectionProvider selectionProvider = getProvider();
			if (selectionProvider != null) {
				selectionProvider.removePostSelectionChangedListener(selectionListener);
			}
		}
	}
	
	private IResourceChangeListener resourceListener = new IResourceChangeListener() {
		
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			// TODO Auto-generated method stub
//			if(event.getType() != IResourceChangeEvent.POST_CHANGE){
//				return;
//			}
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
				         Flog.log("%s\n%s\n%s", delta.getFullPath(), delta.getProjectRelativePath(), delta.getResource().getName());
				         IFileStore store = EFS.getStore(delta.getResource().getLocationURI());
				         Flog.log("%s\n%s", store.fetchInfo().toString(), store.toString());
				         
//				         log(Status.INFO, String.format("%s %s %s", delta.getResource().getName(), delta.getKind(), delta.getFullPath()));
				     return true;
					}});
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	private IPartListener2 iPartListener = new IPartListener2() {
		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);	
			if (!(part instanceof ITextEditor)) {
				return;
			}
//			ITextEditor editor = (ITextEditor) part;
			
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
			DocumentListenerManager documentListener = map.get(part);
			if (documentListener == null) {
				return;
			}
			documentListener.stop();
			map.remove(part);
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			
			if (!(part instanceof IEditorPart)) {
				return;
			}
			IEditorPart editor = (IEditorPart)part;
			DocumentListenerManager documentListener = map.get(editor);
			if (documentListener != null) {
				return;
			}
			DocumentListenerManager dl = new DocumentListenerManager(self, editor);
			dl.start();
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
	};
	
	public void start(IWorkspace workspace, EditorEventHandler editorEventHandler) {
		this.editorEventHandler = editorEventHandler;
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		window.getPartService().addPartListener(iPartListener);
		workspace.addResourceChangeListener(resourceListener);
		IWorkbenchPage[] pages = window.getPages();
		for (IWorkbenchPage page : pages) {
			IEditorReference[] editorReferences = page.getEditorReferences();
			for (IEditorReference editorReference : editorReferences) {
				IEditorPart editor = editorReference.getEditor(false);
				DocumentListenerManager dl = new DocumentListenerManager(this, editor);
				dl.start();
			}
		}
	}
	
	public void stop(IWorkspace iWorkspace) throws Exception {
		context.workspace.removeResourceChangeListener(resourceListener);
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		window.getPartService().addPartListener(iPartListener);
	}

	private IDocument getDocument(IEditorPart editor) {
		if (editor == null) return null;
		IDocumentProvider documentProvider = ((ITextEditor) editor.getAdapter(ITextEditor.class)).getDocumentProvider();
		return documentProvider.getDocument(editor.getEditorInput());
	}

}