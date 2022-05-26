package io.kenxue.cicd.application.common.socket.message;

import io.kenxue.cicd.coreclient.dto.common.response.Response;
import org.springframework.web.socket.WebSocketSession;

/**
 * websocket 信息处理器统一接口
 */
public interface WebSocketMessageHandlerI {
    Response execute(String message, WebSocketSession session);
}