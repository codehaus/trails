package org.trails.i18n;

import org.apache.hivemind.service.ThreadLocale;

import java.util.Locale;

public class DefaultLocaleHolder implements LocaleHolder
{
    private ThreadLocale threadLocale;

    public Locale getLocale()
    {
        if (getThreadLocale() != null)
        {
            return getThreadLocale().getLocale();
        }
        return Locale.getDefault();
    }

    public ThreadLocale getThreadLocale()
    {
        return threadLocale;
    }

    public void setThreadLocale(ThreadLocale threadLocale)
    {
        this.threadLocale = threadLocale;
    }

}
