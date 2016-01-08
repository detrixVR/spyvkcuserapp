package shared.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import webserver.controller.account_service.AccountServiceImpl;
import webserver.controller.account_service.IAccountService;
import shared.controller.api_service.ApiServiceImpl;
import shared.controller.api_service.IApiService;
import shared.controller.db_service.DBServiceImpl;
import shared.controller.db_service.IDBService;
import shared.controller.json_service.IJsonService;
import shared.controller.json_service.JsonServiceImpl;
import shared.controller.link_builder.ILinkBuilder;
import shared.controller.link_builder.LinkBuilderImpl;
import shared.controller.request.IRequest;
import shared.controller.request.RequestImpl;
import webserver.controller.cookies_service.CookiesServiceImpl;
import webserver.controller.cookies_service.ICookiesService;
import webserver.view.templater.IPageGenerator;
import webserver.view.templater.PageGeneratorImpl;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRequest.class).to(RequestImpl.class).in(Singleton.class);
        bind(ILinkBuilder.class).to(LinkBuilderImpl.class).in(Singleton.class);
        bind(IJsonService.class).to(JsonServiceImpl.class).in(Singleton.class);
        bind(IAccountService.class).to(AccountServiceImpl.class).in(Singleton.class);
        bind(ICookiesService.class).to(CookiesServiceImpl.class).in(Singleton.class);
        bind(IPageGenerator.class).to(PageGeneratorImpl.class).in(Singleton.class);
        bind(IDBService.class).to(DBServiceImpl.class).in(Singleton.class);
        bind(IApiService.class).to(ApiServiceImpl.class).in(Singleton.class);
    }
}
