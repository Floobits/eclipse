package floobits;

import io.fletty.channel.nio.NioEventLoopGroup;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;

import floobits.common.Bootstrap;
import floobits.common.FlooUrl;
import floobits.common.protocol.handlers.FlooHandler;
import floobits.impl.ContextImpl;

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
	public static Activator slef;
	public ContextImpl context;
	
	/**
	 * The constructor
	 */
	public Activator() {
		iWorkspace = ResourcesPlugin.getWorkspace();
		slef = this;
		IProduct product = Platform.getProduct();
//		Bundle eclipse = Platform.getBundle("org.eclipse.platform_ plugin");
//		Version ecliVersion = eclipse.getVersion();
		Version flooVersion = FrameworkUtil.getBundle(getClass()).getVersion();
		Version version = product.getDefiningBundle().getVersion();
//		Bootstrap.bootstrap(product.getName(), Integer.toString(version.getMajor()), Integer.toString(version.getMinor()), String.format("%s-%s", flooVersion.getMajor(), flooVersion.getMinor()));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		this.context = new ContextImpl(iWorkspace);
		String path = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toString();
		this.context.joinWorkspace(new FlooUrl("https://floobits.com/kansface/asdf"), path, false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
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
	
	public static void _log(int severity, String msg, int status) {
		slef.getLog().log(new Status(severity, PLUGIN_ID, status, msg, null));
	}
}
