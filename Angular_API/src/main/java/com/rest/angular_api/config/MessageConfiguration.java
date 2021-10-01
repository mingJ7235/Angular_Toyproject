package com.rest.angular_api.config;

import net.rakugakibox.util.YamlResourceBundle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Spring에서 제공하는 LocaleChangeInterceptor를 사용하여 lang이라는 RequestParameter가 요청에 있으면
 * 해당 값을 읽어 로케일 정보를 변경해준다.
 * 로케일 정보는 기본으로Session에서 읽어오고 저장하도록 SessionLocaleResolver를 사용하였는데,
 * 다른 리졸버도 있으므로 상황에 따라 적절한 리졸버를 설정하여 사용하면 된다.
 */
@Configuration
public class MessageConfiguration implements WebMvcConfigurer {

    @Bean //세션에 지역설정. default는 KOREAN = 'ko'
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.KOREAN);
        return slr;
    }

    @Bean //지역 설정을 변경하는 인터셉터. 요청시 파라미터에 lang 정보를 지정하면 언어가 변경된다.
    public LocaleChangeInterceptor localeChangeInterceptor () {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override //인터셉터를 시스템 레지스트리에 등록한다.
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public MessageSource messageSource (
            @Value("${spring.messages.basename}") String basename,
            @Value("${spring.messages.encoding}") String encoding
    ) {
        YamlMessageSource ms = new YamlMessageSource();
        ms.setBasename(basename);
        ms.setDefaultEncoding(encoding);
        ms.setAlwaysUseMessageFormat(true);
        ms.setUseCodeAsDefaultMessage(true);
        ms.setFallbackToSystemLocale(true);
        return ms;
    }

    private static class YamlMessageSource extends ResourceBundleMessageSource {

        @Override
        protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
            return ResourceBundle.getBundle(basename, locale, YamlResourceBundle.Control.INSTANCE);
        }

    }

}
