package cn.van.kuang.guice.in.action;

import java.util.List;

public interface Repository<T> {

    List<T> list();

}
