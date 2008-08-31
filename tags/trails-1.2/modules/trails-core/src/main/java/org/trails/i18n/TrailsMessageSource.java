/*
 * Created on 25/11/2005 by Eduardo Piva - eduardo@gwe.com.br
 *
 */
package org.trails.i18n;

import java.util.Locale;

import org.trails.descriptor.IClassDescriptor;
import org.trails.descriptor.IDescriptor;

/**
 * Interface used to imlement i18n in Trails pages and components.
 */
public interface TrailsMessageSource
{

	Object[] EMPTY_ARGUMENTS = new Object[]{};

	/**
	 * Return a internationalized message.
	 *
	 * @param key Key used to locate the message.
	 * @return Localized message. Return null if message not found.
	 */
	public String getMessage(String key);

	/**
	 * Return a internationalized message specifing arguments to format this message.
	 *
	 * @param key  Key used to locate the message.
	 * @param args Arguments to format the message.
	 * @return Localized message. Return null of message not found.
	 */
	public String getMessage(String key, Object[] args);

	/**
	 * Same as getMessage(String key), but it will return a default value instead of null if message not found.
	 *
	 * @param defaultMessage Default message to return if message not found.
	 */
	public String getMessageWithDefaultValue(String key, String defaultMessage);

	/**
	 * Same as getMessage(String key, Object[] args), but will return a default value instead of null if message not
	 * found.
	 *
	 * @param defaultMessage Default message to return if message not found.
	 */
	public String getMessageWithDefaultValue(String key, Object[] args, String defaultMessage);

	/**
	 * Given a IDescriptor, this method select an i18n message for the descriptor. If no i18n message is found, a
	 * defaultMessage is used instead.
	 *
	 * @param clazz			  Class description.
	 * @param defaultDisplayName default displayName to return if no i18n message is found.
	 * @return
	 */
	public String getDisplayName(IDescriptor clazz, String defaultDisplayName);

	/**
	 * Given a IClassDescriptor and a Locale, this method select an i18n message for the class in the plural name
	 *
	 * @param clazz			  Class description
	 * @param defaultDisplayName default displayName to return if no i18n message is found.
	 * @return
	 */
	public String getPluralDislayName(IClassDescriptor clazz, String defaultDisplayName);


	/**
	 * Return a internationalized message.
	 * <p/>
	 * Sometimes it makes sense to force a Locale, but don't overuse this method.
	 *
	 * @param key	Key used to locate the message.
	 * @param locale Locale used to locate message.
	 * @return Localized message. Return null if message not found.
	 */
	public String getMessage(String key, Locale locale);

	/**
	 * Return a internationalized message specifing arguments to format this message.
	 * <p/>
	 * Sometimes it makes sense to force a Locale, but don't overuse this method.
	 *
	 * @param key	Key used to locate the message.
	 * @param args   Arguments to format the message.
	 * @param locale Locale used to locate message.
	 * @return Localized message. Return null of message not found.
	 */
	public String getMessage(String key, Object[] args, Locale locale);

	public String getMessageWithDefaultValue(String key, Locale locale, String defaultMessage);
	public String getMessageWithDefaultValue(String key, Object[] args, Locale locale, String defaultMessage);
}
