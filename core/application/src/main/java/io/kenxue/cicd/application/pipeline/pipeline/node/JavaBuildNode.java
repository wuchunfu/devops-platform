package io.kenxue.cicd.application.pipeline.pipeline.node;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import io.kenxue.cicd.application.pipeline.logger.node.service.PipelineExecuteLoggerSocketService;
import io.kenxue.cicd.domain.domain.pipeline.NodeLogger;
import io.kenxue.cicd.domain.factory.pipeline.NodeExecuteLoggerFactory;
import io.kenxue.cicd.domain.repository.pipeline.NodeExecuteLoggerRepository;
import io.kenxue.cicd.sharedataboject.pipeline.context.DefaultResult;
import io.kenxue.cicd.sharedataboject.pipeline.context.ExecuteContext;
import io.kenxue.cicd.sharedataboject.pipeline.context.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Properties;

/**
 * java构建step
 */
@Service
@Slf4j
public class JavaBuildNode extends AbstractNode {

    @Override
    public Result execute(ExecuteContext context) {

        NodeLogger logger = NodeExecuteLoggerFactory.getNodeExecuteLogger().setExecuteStartTime(new Date());
        log.error(getName());
        Object attributes = context.getAttributes(getName());
        DefaultResult defaultResult = new DefaultResult();
        log.info("attr : {}", attributes);

        shell(logger, context, new String[]{
                "cd /home/admin/"
                , "git -v"
                , "rm -rf cicd-platform"
                , "git clone https://gitee.com/ken_xue/cicd-platform.git"
                , "cd cicd-platform"
                , "mvn -v"
                , "mvn clean install -Dmaven.test.skip=true"
                , "cd starter/"
                , "ls target"
                , "tar -cvfz target/cicd-platform.jar package.tar.gz"
        });

        log.error("{}节点执行完成", getName());
        return defaultResult;
    }

    @Override
    public String getName() {
        return "JAVA_BUILD";
    }

}

