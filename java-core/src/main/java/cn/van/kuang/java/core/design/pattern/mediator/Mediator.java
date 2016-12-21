package cn.van.kuang.java.core.design.pattern.mediator;

import cn.van.kuang.java.core.design.pattern.mediator.msg.GroupByTypeMessage;
import cn.van.kuang.java.core.design.pattern.mediator.msg.Message;
import cn.van.kuang.java.core.design.pattern.mediator.msg.ToSpecificMemberMessage;
import cn.van.kuang.java.core.design.pattern.mediator.user.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Mediator {

    private Map<Role.Type, List<Role>> staffsGroupByType = new ConcurrentHashMap<>();
    private Set<Role> allStaffs = Collections.synchronizedSet(new HashSet<>());

    public void register(Role... staffs) {
        for (Role staff : staffs) {
            doRegister(staff);
        }
    }

    public void onMessage(Message msg) {
        System.out.println(msg.from().name() + " said \"" + msg.content() + "\"");

        String content = "[From " + msg.from().name() + "]--\"" + msg.content() + "\"";

        if (msg instanceof GroupByTypeMessage) {
            GroupByTypeMessage groupByTypeMessage = (GroupByTypeMessage) msg;
            List<Role.Type> toTypes = groupByTypeMessage.to();

            toTypes.stream()
                    .filter(type -> staffsGroupByType.containsKey(type))
                    .forEach(type -> {
                        List<Role> staffs = staffsGroupByType.get(type);
                        for (Role staff : staffs) {
                            staff.onMessage(content);
                        }
                    });
        } else if (msg instanceof ToSpecificMemberMessage) {
            ToSpecificMemberMessage toSpecificMemberMessage = (ToSpecificMemberMessage) msg;

            for (Role staff : allStaffs) {
                if (staff.equals(toSpecificMemberMessage.to().get(0))) {
                    staff.onMessage(content);
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("Invalidate message type: [" + msg.getClass() + "]");
        }
    }

    private void doRegister(Role staff) {
        allStaffs.add(staff);

        Role.Type type = staff.type();

        if (staffsGroupByType.containsKey(type)) {
            staffsGroupByType.get(type).add(staff);
        } else {
            List<Role> staffList = new ArrayList<>();
            staffList.add(staff);
            staffsGroupByType.put(type, staffList);
        }
    }

    public static void main(String[] args) {
        Developer van = new Developer("Van", "JAVA");
        Developer rex = new Developer("Rex", "Python");
        Manager wilson = new Manager("Wilson");
        Tester cindy = new Tester("Cindy");
        BusinessAnalyst himanshu = new BusinessAnalyst("Himanshu");

        Mediator mediator = new Mediator();
        mediator.register(van, rex, wilson, cindy, himanshu);

        Message msg = new GroupByTypeMessage(
                wilson,
                Arrays.asList(Role.Type.DEV, Role.Type.BA, Role.Type.TESTER),
                "stand up");
        mediator.onMessage(msg);

        Message toVanMsg = new ToSpecificMemberMessage(wilson, van, "come to my desk");
        mediator.onMessage(toVanMsg);
    }
}
