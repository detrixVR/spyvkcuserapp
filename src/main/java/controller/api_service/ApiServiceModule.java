package controller.api_service;

import com.google.inject.AbstractModule;
import controller.link_builder.ILinkBuilder;
import controller.link_builder.LinkBuilderImpl;
import controller.json_service.IJsonService;
import controller.json_service.JsonServiceImpl;
import controller.request.IRequest;
import controller.request.RequestImpl;

public class ApiServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRequest.class).to(RequestImpl.class);
        bind(ILinkBuilder.class).to(LinkBuilderImpl.class);
        bind(IJsonService.class).to(JsonServiceImpl.class);
    }
}
