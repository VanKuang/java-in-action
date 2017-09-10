package cn.van.kuang.guice.in.action;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Arrays;
import java.util.List;

public class FundController implements Controller {

    private final FundService fundService;
    private final Service priceService;
    private final Service traderService;

    @Inject
    public FundController(FundService fundService,
                          @Named("PriceService") Service priceService,
                          @TraderServiceAnnotation Service traderService) {
        this.fundService = fundService;
        this.priceService = priceService;
        this.traderService = traderService;
    }

    public void listAllFund() {
        System.out.println("======ALL FUNDS======");
        System.out.println(fundService.list());
    }

    public List<Service> getAllService() {
        return Arrays.asList(fundService, priceService, traderService);
    }
}
