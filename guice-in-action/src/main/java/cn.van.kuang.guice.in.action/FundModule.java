package cn.van.kuang.guice.in.action;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class FundModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<Repository<Fund>>() {
        }).to(FundRepository.class);
        bind(Service.class).to(FundService.class);
    }

}
