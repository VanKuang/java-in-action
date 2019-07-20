package com.van.kuang.jgroups;

import org.jgroups.JChannel;

public class ChannelFactory {

    public JChannel createChannel() throws Exception {
        return new JChannel("/udp");
    }

}
