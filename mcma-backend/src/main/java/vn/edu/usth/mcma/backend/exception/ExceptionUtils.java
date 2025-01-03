package vn.edu.usth.mcma.backend.exception;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

public class ExceptionUtils {
    public static Locale getLocaleResolver(HttpServletRequest request) {
        try {
            Locale locale;
            String lang = request.getHeader("lang");
            if (StringUtils.isNotEmpty(lang)) {
                locale = new Locale(lang);
            } else {
                LocaleResolver localeResolver = (LocaleResolver) request.getAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE);
                locale = localeResolver.resolveLocale(request);
            }
            return locale;
        } catch (Exception e) {
            return Locale.forLanguageTag("en");
        }
    }
    public static String getStackTrace(MessageSource messageSource, HttpServletRequest request, String stackTrace) {
        try {
            Locale locale = ExceptionUtils.getLocaleResolver(request);
            return messageSource.getMessage(stackTrace, null, locale);
        } catch (Exception e) {
            return null;
        }
    }
}
