package cn.van.kuang.guice.in.action;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.List;

public class App {

    public static void main(String[] args) {
        Injector fundInjector = Guice.createInjector(new FundModule());

        FundService fundService = fundInjector.getInstance(FundService.class);

        System.out.println(fundService.name());
        System.out.println(fundService.list());

        FundController fundController = fundInjector.getInstance(FundController.class);
        fundController.listAllFund();

        List<Service> allService = fundController.getAllService();

        allService.forEach(service -> System.out.println(service.name()));
    }

}
