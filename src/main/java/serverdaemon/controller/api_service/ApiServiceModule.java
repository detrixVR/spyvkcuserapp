package serverdaemon.controller.api_service;

import com.google.inject.AbstractModule;
import serverdaemon.controller.link_builder.ILinkBuilder;
import serverdaemon.controller.link_builder.LinkBuilderImpl;
import serverdaemon.controller.json_service.IJsonService;
import serverdaemon.controller.json_service.JsonServiceImpl;
import serverdaemon.controller.request.IRequest;
import serverdaemon.controller.request.RequestImpl;

public class ApiServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRequest.class).to(RequestImpl.class);
        bind(ILinkBuilder.class).to(LinkBuilderImpl.class);
        bind(IJsonService.class).to(JsonServiceImpl.class);
    }
}
