package floobits.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import floobits.common.interfaces.IFile;
import floobits.utilities.Flog;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.IFileBuffer;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class FileImpl extends IFile {
	public org.eclipse.core.resources.IFile file;
	protected IFileBuffer buffer;

	public FileImpl(org.eclipse.core.resources.IFile file) {
		this.file = file;
		FileBuffers.getTextFileBufferManager();
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return file.getLocation().toString();
	}

	@Override
	public boolean rename(Object obj, String name) {
//		// TODO Auto-generated method stub
//		IPath path = file.getLocation();
//		path.uptoSegment(0).append(name);
//		file.getFileStore().g
//		file.getFileStore().move(path., 0, null)
		return false;
	}

	@Override
	public IFile makeFile(String name) {
		IPath path = file.getLocation().append(name);
		try {
			, 0, null);
		} catch (CoreException e) {
			Flog.warn(e);
			return null;
		}
		return true;
	}

	@Override
	public boolean move(Object obj, IFile d) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Object obj) {
		try {
			file.delete(true, null);
		} catch (CoreException e) {
			Flog.warn(e);
			return false;
		}
		return true;
	}

	@Override
	public IFile[] getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return file.getName();
	}

	@Override
	public long getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exists() {
		return file.exists();
	}

	@Override
	public boolean isDirectory() {
		// TODO Auto-generated method stub
		return file.get;
	}

	@Override
	public boolean isSpecial() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSymLink() {
		return file.isLinked();
	}

	@Override
	public boolean isValid() {
		return !file.isVirtual() && !file.isPhantom();
	}

	@Override
	public byte[] getBytes() {
		InputStream in;
		try {
			in = file.getContents();
		} catch (CoreException e) {
			Flog.warn(e);
			return null;
		}
		byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(in);
		} catch (IOException e) {
			Flog.warn(e);
			return null;
		}
		return bytes;
	}

	@Override
	public boolean setBytes(byte[] bytes) {
		ByteArrayInputStream io = new ByteArrayInputStream(bytes);
		try {
			file.setContents(io, true, true, null);
		} catch (CoreException e) {
			Flog.warn(e);
			return false;
		}
		return true;
	}

	@Override
	public void refresh() {
		try {
			file.refreshLocal(50, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			Flog.warn(e);
		}
		
	}

	@Override
	public boolean createDirectories(String dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InputStream getInputStream() {
		// TODO Auto-generated method stub
		try {
			return file.getContents();
		} catch (CoreException e) {
			Flog.warn(e);
		}
		return null;
	}

}
