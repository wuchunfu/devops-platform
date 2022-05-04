package io.kenxue.cicd.application.pipeline.pipeline.node;

import io.kenxue.cicd.sharedataboject.pipeline.context.ExecuteContext;
import io.kenxue.cicd.sharedataboject.pipeline.context.Result;
import io.kenxue.cicd.sharedataboject.pipeline.node.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EndNode implements Node {

    @Override
    public Result execute(ExecuteContext executeContext) {
        log.info(getName());
        return null;
    }

    @Override
    public String getName() {
        return "END";
    }
}
