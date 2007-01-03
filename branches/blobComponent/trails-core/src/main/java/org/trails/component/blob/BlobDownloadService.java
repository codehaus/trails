package org.trails.component.blob;

import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.error.RequestExceptionReporter;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.util.ContentType;
import org.apache.tapestry.web.WebResponse;
import org.trails.descriptor.BlobDescriptorExtension;
import org.trails.descriptor.DescriptorService;
import org.trails.persistence.PersistenceService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class BlobDownloadService implements IEngineService {

    private static final Log LOG = LogFactory.getLog(BlobDownloadService.class);

    public static final String SERVICE_NAME = "BinaryOutPutService";

    private RequestExceptionReporter _exceptionReporter;

    private LinkFactory _linkFactory;

    private WebResponse _response;

    private PersistenceService persistenceService;

    private DescriptorService descriptorService;

    public PersistenceService getPersistenceService() {
        return persistenceService;
    }

    public void setPersistenceService(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }


    public DescriptorService getDescriptorService() {
        return descriptorService;
    }

    public void setDescriptorService(DescriptorService descriptorService) {
        this.descriptorService = descriptorService;
    }

    private static final String BINOUTID = "id";

    private static final String ENTITY_NAME = "class";

    private static final String BYTES_PROPERTY = "property";

    public ILink getLink(boolean post, Object parameter) {

        Defense.isAssignable(((Object[]) parameter)[0], TrailsBlobAsset.class, "parameter");

        TrailsBlobAsset asset = (TrailsBlobAsset) ((Object[]) parameter)[0];

        Map parameters = new HashMap();

        parameters.put(BINOUTID, String.valueOf(asset.getIdProperty()));
        parameters.put(ENTITY_NAME, asset.getEntityName());
        parameters.put(BYTES_PROPERTY, asset.getBytesProperty());

        return _linkFactory.constructLink(this, false, parameters, true);

    }

    public void service(IRequestCycle cycle) throws IOException {

        String binoutID = cycle.getParameter(BINOUTID);
        String entityName = cycle.getParameter(ENTITY_NAME);
        String bytesProp = cycle.getParameter(BYTES_PROPERTY);

        try {
            BlobDescriptorExtension blobDescriptor = getDescriptorService().getClassDescriptor(Class.forName(entityName)).getPropertyDescriptor(bytesProp).getExtension(BlobDescriptorExtension.class);

            Object model = getPersistenceService().getInstance(Class.forName(entityName), Integer.valueOf(binoutID));
            byte[] bytes = new byte[0];

            String fileName = bytesProp;
            String contentType = "image/jpeg";

            if (blobDescriptor.isBytes()) {

                bytes = (byte[]) Ognl.getValue(bytesProp, model);

                if (!"".equals(blobDescriptor.getContentType()))
                    contentType = blobDescriptor.getContentType();

                if (!"".equals(blobDescriptor.getFileName()))
                    fileName = blobDescriptor.getFileName();

            } else if (blobDescriptor.isITrailsBlob()) {

                ITrailsBlob trailsBlob = (ITrailsBlob) Ognl.getValue(bytesProp, model);
                if (trailsBlob != null) {
                    bytes = trailsBlob.getBytes();
                    contentType = !"".equals(blobDescriptor.getContentType()) ? blobDescriptor.getContentType() : trailsBlob.getContentType();
                    fileName = !"".equals(blobDescriptor.getFileName()) ? blobDescriptor.getFileName() : trailsBlob.getFileName();
                }
            }

            _response.setHeader("Expires", "0");
            _response.setHeader("Cache-Control", "must-revalidate, post-check=0,pre-check=0");
            _response.setHeader("Pragma", "public");
//            _response.setHeader("Content-disposition", "inline; filename=" + "newName.txt");            
            _response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            OutputStream output = _response.getOutputStream(new ContentType(contentType));
            output.write(bytes);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OgnlException e) {
            e.printStackTrace();
        }

        return;
    }

    public String getName() {
        return SERVICE_NAME;
    }

    public void setExceptionReporter(RequestExceptionReporter exceptionReporter) {
        _exceptionReporter = exceptionReporter;
    }

    public void setLinkFactory(LinkFactory linkFactory) {
        _linkFactory = linkFactory;
    }

    public void setResponse(WebResponse response) {
        _response = response;
    }
}