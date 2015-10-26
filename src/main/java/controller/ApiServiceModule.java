package controller;

import com.google.inject.AbstractModule;

public class ApiServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRequest.class).to(RequestImpl.class);
        bind(ILinkBuilder.class).to(LinkBuilderImpl.class);
        bind(IJsonService.class).to(JsonServiceImpl.class);
    }
}
