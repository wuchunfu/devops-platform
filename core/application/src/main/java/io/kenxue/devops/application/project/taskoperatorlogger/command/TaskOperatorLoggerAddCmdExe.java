package io.kenxue.devops.application.project.taskoperatorlogger.command;

import io.kenxue.devops.application.project.taskoperatorlogger.assembler.TaskOperatorLogger2DTOAssembler;
import io.kenxue.devops.coreclient.dto.common.response.Response;
import io.kenxue.devops.domain.repository.project.TaskOperatorLoggerRepository;
import io.kenxue.devops.domain.domain.project.TaskOperatorLogger;
import io.kenxue.devops.coreclient.dto.project.taskoperatorlogger.TaskOperatorLoggerAddCmd;
import org.springframework.stereotype.Component;
import jakarta.annotation.Resource;
/**
 * 项目任务操作日志
 * @author 麦奇
 * @date 2022-06-04 00:21:17
 */
@Component
public class TaskOperatorLoggerAddCmdExe {

    @Resource
    private TaskOperatorLoggerRepository taskOperatorLoggerRepository;
    @Resource
    private TaskOperatorLogger2DTOAssembler taskOperatorLogger2DTOAssembler;

    public Response execute(TaskOperatorLoggerAddCmd cmd) {
        TaskOperatorLogger taskOperatorLogger = taskOperatorLogger2DTOAssembler.toDomain(cmd.getTaskOperatorLoggerDTO());
        taskOperatorLogger.create();
        taskOperatorLoggerRepository.create(taskOperatorLogger);
        return Response.success();
    }
}
