package cn.van.kuang.jersey.jetty.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BigObject {

    private String content;

    public BigObject() {
    }

    public BigObject(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BigObject{" +
                "content='" + content + '\'' +
                '}';
    }
}
