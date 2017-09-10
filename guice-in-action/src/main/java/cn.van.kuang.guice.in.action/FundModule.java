package cn.van.kuang.guice.in.action;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class FundModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<Repository<Fund>>() {
        }).to(FundRepository.class);
        bind(Service.class).to(FundService.class);
        bind(Service.class).annotatedWith(Names.named("PriceService")).to(PriceService.class);
        bind(Service.class).annotatedWith(TraderServiceAnnotation.class).to(TraderService.class);
    }

}
