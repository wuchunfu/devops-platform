package io.kenxue.cicd.application.pipeline.pipeline.service;

import com.alibaba.fastjson.JSON;
import io.kenxue.cicd.application.pipeline.pipeline.command.ApplicationPipelineExecuteCmdExe;
import io.kenxue.cicd.application.pipeline.pipeline.socket.PipelineExecuteSocketService;
import io.kenxue.cicd.coreclient.dto.pipeline.pipeline.PushNodeExecuteStatusDTO;
import io.kenxue.cicd.domain.domain.pipeline.Pipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
public class PipelineExecuteSocketServiceImpl implements PipelineExecuteSocketService {

    //存放ssh连接信息的map
    private static Map<String, Queue<WebSocketSession>> webSocketConnectionPool = new ConcurrentHashMap<>(2<<4);

    @Resource
    private ApplicationPipelineExecuteCmdExe applicationPipelineExecuteCmdExe;

    @Override
    public void initConnection(WebSocketSession session) {
        URI uri = session.getUri();
        String key = uri.getQuery();
        try {
            session.sendMessage(new TextMessage("pong".getBytes()));
            Pipeline pipeline = applicationPipelineExecuteCmdExe.get(key);
            session.sendMessage(new TextMessage(JSON.toJSONString(pipeline).getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Queue<WebSocketSession> queue = webSocketConnectionPool.getOrDefault(key, new ConcurrentLinkedQueue<>());
        log.error("加入连接池 key:{} queue:{}",key,queue);
        queue.offer(session);
        webSocketConnectionPool.put(key,queue);
        session.getAttributes().put("key",key);
    }

    @Override
    public void recvHandle(String buffer, WebSocketSession session) {

    }

    @Override
    public void sendMessage(String key, PushNodeExecuteStatusDTO message) {
        Queue<WebSocketSession> webSocketSessions = webSocketConnectionPool.get(key);
        log.error("推送信息:{},message:{}",webSocketSessions.size(),message);
        for (WebSocketSession conn : webSocketSessions) {
            try {
                conn.sendMessage(new TextMessage(message.toString().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close(WebSocketSession session) {
        log.info("close session:{}",session);
        webSocketConnectionPool.get(session.getAttributes().get("key")).remove(session);
    }
}
