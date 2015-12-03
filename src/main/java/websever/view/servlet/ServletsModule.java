package websever.view.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import serverdaemon.controller.account_service.AccountServiceImpl;
import serverdaemon.controller.account_service.IAccountService;
import serverdaemon.controller.api_service.ApiServiceImpl;
import serverdaemon.controller.api_service.ApiServiceModule;
import serverdaemon.controller.api_service.IApiService;
import serverdaemon.controller.cookies_service.CookiesServiceImpl;
import serverdaemon.controller.cookies_service.ICookiesService;
import shared.controller.db_service.DBServiceImpl;
import shared.controller.db_service.IDBService;
import serverdaemon.controller.logic.AppLogicImpl;
import serverdaemon.controller.logic.IAppLogic;
import websever.view.templater.IPageGenerator;
import websever.view.templater.PageGeneratorImpl;

public class ServletsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IAccountService.class).to(AccountServiceImpl.class).in(Singleton.class);
        bind(ICookiesService.class).to(CookiesServiceImpl.class).in(Singleton.class);
        bind(IPageGenerator.class).to(PageGeneratorImpl.class).in(Singleton.class);
        bind(IAppLogic.class).to(AppLogicImpl.class).in(Singleton.class);
        bind(IDBService.class).to(DBServiceImpl.class).in(Singleton.class);

        Injector apiServiceInjector = Guice.createInjector(new ApiServiceModule());
        IApiService apiService = apiServiceInjector.getInstance(ApiServiceImpl.class);
        bind(IApiService.class).toInstance(apiService);
    }
}
