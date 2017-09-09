package cn.van.kuang.guice.in.action;

import com.google.inject.Inject;

import java.util.List;

public class FundService implements Service {

    private final Repository<Fund> repository;

    @Inject
    public FundService(Repository<Fund> repository) {
        this.repository = repository;
    }

    @Override
    public String name() {
        return "FundService";
    }

    public List<Fund> list() {
        return repository.list();
    }
}
