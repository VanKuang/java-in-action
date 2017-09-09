package cn.van.kuang.guice.in.action;

import com.google.inject.Inject;

public class FundController implements Controller {

    private final FundService service;

    @Inject
    public FundController(FundService service) {
        this.service = service;
    }

    public void listAll() {
        System.out.println("======ALL FUNDS======");
        System.out.println(service.list());
    }
}
