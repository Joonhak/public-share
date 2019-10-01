package org.slam.publicshare.config;

import org.slam.publicshare.account.domain.Account;
import org.slam.publicshare.error.ApiErrorHandler;
import org.springframework.core.MethodParameter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.slam.publicshare.account.utils.AccountUtils.buildNormalAccount;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class WithAuthenticationPrincipal {

    private HandlerMethodArgumentResolver putAuthenticationPrincipal = new HandlerMethodArgumentResolver() {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType().isAssignableFrom(Account.class);
        }

        @Override
        public Object resolveArgument(
                MethodParameter parameter, ModelAndViewContainer mavContainer
                , NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

            var account = buildNormalAccount();
            var idField = account.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(account, 1L);

            return account;
        }
    };

    protected MockMvc setup(Object... controllers) {
        return MockMvcBuilders
                .standaloneSetup(controllers)
                .setControllerAdvice(ApiErrorHandler.class)
                .setCustomArgumentResolvers(putAuthenticationPrincipal)
                .alwaysDo(print())
                .build();
    }

}
