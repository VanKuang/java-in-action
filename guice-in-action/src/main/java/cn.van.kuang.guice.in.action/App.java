package cn.van.kuang.guice.in.action;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class App {

    public static void main(String[] args) {
        Injector fundInjector = Guice.createInjector(new FundModule());

        FundService fundService = fundInjector.getInstance(FundService.class);

        System.out.println(fundService.name());
        System.out.println(fundService.list());

        FundController fundController = fundInjector.getInstance(FundController.class);
        fundController.listAll();
    }

}
