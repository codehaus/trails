/* vim: set ts=2 et sw=2 cindent fo=qroca: */

package org.trails.descriptor.annotation;

import org.apache.commons.lang.Validate;
import org.trails.descriptor.IPropertyDescriptor;

/**
 * Creates a {@link PossibleValuesDescriptorExtension} using the
 * information retrieved from a {@link PossibleValues} annotation.
 *
 * @author pruggia
 */
public class PossibleValuesAnnotationHandler extends AbstractAnnotationHandler
		implements DescriptorAnnotationHandler<PossibleValues, IPropertyDescriptor>
{

	/**
	 * Creates a {@link PossibleValuesDescriptorExtension} and adds it to the
	 * property descriptor.
	 *
	 * @param annotation Annotation added to the property. It cannot be null.
	 * @param descriptor The property descriptor. It cannot be null.
	 * @return Returns descriptor, with the possible values extension.
	 */
	public IPropertyDescriptor decorateFromAnnotation(final PossibleValues annotation,
													  final IPropertyDescriptor descriptor)
	{
		Validate.notNull(annotation, "The annotation cannot be null");
		Validate.notNull(descriptor, "The descriptor cannot be null");

		PossibleValuesDescriptorExtension extension = new PossibleValuesDescriptorExtension(annotation.value());
		descriptor.addExtension(PossibleValuesDescriptorExtension.class.getName(), extension);
		return descriptor;
	}
}

