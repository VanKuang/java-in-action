package cn.van.kuang.java.core.design.pattern.mediator.msg;

import cn.van.kuang.java.core.design.pattern.mediator.user.Role;

import java.util.List;

public class GroupByTypeMessage implements Message<Role.Type> {

    private final Role from;
    private final List<Role.Type> to;
    private final String content;

    public GroupByTypeMessage(Role from, List<Role.Type> to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public Role from() {
        return from;
    }

    public List<Role.Type> to() {
        return to;
    }

    public String content() {
        return content;
    }
}
