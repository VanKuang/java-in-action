package cn.van.kuang.java.core.design.pattern.mediator.msg;

import cn.van.kuang.java.core.design.pattern.mediator.user.Role;

import java.util.List;

public interface Message<T> {

    Role from();

    List<T> to();

    String content();
}
