package org.trails.component.blob;

import ognl.Ognl;
import ognl.OgnlException;
import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.annotations.ComponentClass;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.trails.descriptor.extension.BlobDescriptorExtension;
import org.trails.descriptor.extension.ITrailsBlob;
import org.trails.descriptor.IClassDescriptor;
import org.trails.descriptor.IPropertyDescriptor;
import org.trails.persistence.PersistenceService;

@ComponentClass(allowBody = true, allowInformalParameters = true)
public abstract class TrailsDownload extends BaseComponent
{
	@InjectObject("service:trails.core.BlobService")
	public abstract BlobDownloadService getDownloadService();

	@InjectObject("service:trails.core.PersistenceService")
	public abstract PersistenceService getPersistenceService();

	@Parameter(required = true, cache = true)
	public abstract Object getBytes();

	public abstract void setBytes(Object bytes);

	@Parameter(required = true, cache = true)
	public abstract Object getModel();

	public abstract void setModel(Object bytes);

	@Parameter(required = false, defaultValue = "page.classDescriptor", cache = true)
	public abstract IClassDescriptor getClassDescriptor();

	public abstract void setClassDescriptor(IClassDescriptor ClassDescriptor);

	@Parameter(required = true, cache = true)
	public abstract IPropertyDescriptor getPropertyDescriptor();

	public abstract void setPropertyDescriptor(IPropertyDescriptor descriptor);

	public IPropertyDescriptor getIdentifierDescriptor()
	{
		return getClassDescriptor().getIdentifierDescriptor();
	}

	public BlobDescriptorExtension getBlobDescriptorExtension()
	{
		return getPropertyDescriptor().getExtension(BlobDescriptorExtension.class);
	}

	public synchronized IAsset getByteArrayAsset()
	{
		String id = "";
		try
		{
			if (Ognl.getValue(getIdentifierDescriptor().getName(), getModel()) != null)
				id = Ognl.getValue(getIdentifierDescriptor().getName(),
					getModel()).toString();
		} catch (OgnlException e)
		{
			e.printStackTrace();

			// To change body of catch statement use
			// File | Settings | File Templates.
		}

		ITrailsBlob trailsBlob = null;
		String contentType = null;
		String fileName = null;

		if (getBlobDescriptorExtension().isBytes())
		{
			trailsBlob = (ITrailsBlob) getModel();
		} else if (getBlobDescriptorExtension().isITrailsBlob())
		{
			trailsBlob = (ITrailsBlob) getBytes();
		}
		contentType = trailsBlob.getContentType();
		fileName = trailsBlob.getFileName();

		return new TrailsBlobAsset(getDownloadService(), getClassDescriptor()
			.getType().getName(), id, getPropertyDescriptor().getName(),
			contentType, fileName);
	}
}
