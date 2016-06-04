package cn.van.kuang.netty5x.client.request;

import cn.van.kuang.netty5x.model.Request;
import cn.van.kuang.netty5x.model.Response;

public interface Requester {

    <T> Response<T> request(Request request) throws Exception;

}
