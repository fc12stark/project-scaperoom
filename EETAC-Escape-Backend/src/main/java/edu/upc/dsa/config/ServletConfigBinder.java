package edu.upc.dsa.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;

public class ServletConfigBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(makeServletConfig()).to(ServletConfig.class);
    }

    private ServletConfig makeServletConfig() {
        return (ServletConfig) Proxy.newProxyInstance(
                ServletConfig.class.getClassLoader(),
                new Class<?>[]{ServletConfig.class},
                new DummyHandler());
    }

    private static class DummyHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            String name = method.getName();
            if (name.equals("getServletName")) {
                return "EETACEscape";
            }
            if (name.equals("getServletContext")) {
                return Proxy.newProxyInstance(
                        ServletContext.class.getClassLoader(),
                        new Class<?>[]{ServletContext.class},
                        new DummyHandler());
            }
            if (name.equals("getInitParameterNames") || name.equals("getAttributeNames")) {
                return Collections.enumeration(new ArrayList<String>());
            }
            if (name.equals("toString")) {
                return "DummyServletConfig";
            }
            if (name.equals("hashCode")) {
                return System.identityHashCode(proxy);
            }
            if (name.equals("equals")) {
                return proxy == args[0];
            }
            Class<?> type = method.getReturnType();
            if (type == boolean.class) {
                return false;
            }
            if (type == int.class) {
                return 0;
            }
            if (type == long.class) {
                return 0L;
            }
            return null;
        }
    }
}
