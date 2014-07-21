package floobits.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import floobits.common.interfaces.IFile;
import floobits.utilities.Flog;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.filebuffers.IFileBuffer;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class FileImpl extends IFile {
	public IFileBuffer file;

	public FileImpl(IFileBuffer file) {
		this.file = file;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean move(Object obj, IFile d) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IFile[] getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDirectory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSpecial() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSymLink() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] getBytes() {
		InputStream in;
		try {
			in = file.getFileStore().openInputStream(EFS.NONE, null);
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
		OutputStream out;
		try {
			out = file.getFileStore().openOutputStream(EFS.NONE, null);
		} catch (CoreException e) {
			Flog.warn(e);
			return false;
		}
		try {
			IOUtils.write(bytes, out);
		} catch (IOException e) {
			Flog.warn(e);
			return false;
		}
		return true;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean createDirectories(String dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InputStream getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
