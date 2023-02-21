package org.myorg.framework.notation;

import java.lang.reflect.Method;
import java.util.List;

public interface Request {
    public List<String> getHeader();
    public Method getMethod();
    public ContentType getContentType();
    public boolean useFiles();
}
