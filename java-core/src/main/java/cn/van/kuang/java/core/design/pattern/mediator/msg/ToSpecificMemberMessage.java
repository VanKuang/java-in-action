package cn.van.kuang.java.core.design.pattern.mediator.msg;

import cn.van.kuang.java.core.design.pattern.mediator.user.Role;

import java.util.Collections;
import java.util.List;

public class ToSpecificMemberMessage implements Message<Role> {

    private final Role from;
    private final Role to;
    private final String content;

    public ToSpecificMemberMessage(Role from, Role to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    @Override
    public Role from() {
        return from;
    }

    @Override
    public List<Role> to() {
        return Collections.singletonList(to);
    }

    @Override
    public String content() {
        return content;
    }
}
