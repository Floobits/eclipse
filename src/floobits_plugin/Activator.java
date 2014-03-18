package floobits_plugin;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */ 
@SuppressWarnings("unused")
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "floobits-plugin"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	public static IWorkspace iWorkspace;
	private static Listener listener = new Listener();
	public static Activator slef;
	
	/**
	 * The constructor
	 */
	public Activator() {
		iWorkspace = ResourcesPlugin.getWorkspace();
		slef = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		listener.stop(iWorkspace);
		iWorkspace = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	public void _log(int severity, String msg) {
		 getLog().log(new Status(severity, PLUGIN_ID, Status.OK, msg, null));
	}
	
	public static void log(String msg) {
		slef._log(IStatus.OK, msg);
	}
	
	public static void joinWorkspace(IWorkbenchWindow window) {
		listener.start(window, Activator.iWorkspace);
	}
}
