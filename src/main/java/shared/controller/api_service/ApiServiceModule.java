package shared.controller.api_service;

import com.google.inject.AbstractModule;
import shared.controller.json_service.IJsonService;
import shared.controller.json_service.JsonServiceImpl;
import shared.controller.link_builder.ILinkBuilder;
import shared.controller.link_builder.LinkBuilderImpl;
import shared.controller.request.IRequest;
import shared.controller.request.RequestImpl;

public class ApiServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRequest.class).to(RequestImpl.class);
        bind(ILinkBuilder.class).to(LinkBuilderImpl.class);
        bind(IJsonService.class).to(JsonServiceImpl.class);
    }
}
