package shared.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import serverdaemon.controller.logic.AppLogicImpl;
import serverdaemon.controller.logic.IAppLogic;
import shared.controller.account_service.AccountServiceImpl;
import shared.controller.account_service.IAccountService;
import shared.controller.api_service.ApiServiceImpl;
import shared.controller.api_service.ApiServiceModule;
import shared.controller.api_service.IApiService;
import shared.controller.db_service.DBServiceImpl;
import shared.controller.db_service.IDBService;
import webserver.controller.cookies_service.CookiesServiceImpl;
import webserver.controller.cookies_service.ICookiesService;
import webserver.view.templater.IPageGenerator;
import webserver.view.templater.PageGeneratorImpl;

public class ApplicationModule extends AbstractModule {
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
