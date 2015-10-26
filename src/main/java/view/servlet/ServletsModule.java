package view.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import controller.account_service.AccountServiceImpl;
import controller.account_service.IAccountService;
import controller.api_service.ApiServiceImpl;
import controller.api_service.ApiServiceModule;
import controller.api_service.IApiService;
import controller.cookies_service.CookiesServiceImpl;
import controller.cookies_service.ICookiesService;
import controller.logic.AppLogicImpl;
import controller.logic.IAppLogic;
import view.templater.IPageGenerator;
import view.templater.PageGeneratorImpl;

public class ServletsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IAccountService.class).to(AccountServiceImpl.class).in(Singleton.class);
        bind(ICookiesService.class).to(CookiesServiceImpl.class).in(Singleton.class);
        bind(IPageGenerator.class).to(PageGeneratorImpl.class).in(Singleton.class);
        bind(IAppLogic.class).to(AppLogicImpl.class).in(Singleton.class);

        Injector apiServiceInjector = Guice.createInjector(new ApiServiceModule());
        IApiService apiService = apiServiceInjector.getInstance(ApiServiceImpl.class);
        bind(IApiService.class).toInstance(apiService);
    }
}
