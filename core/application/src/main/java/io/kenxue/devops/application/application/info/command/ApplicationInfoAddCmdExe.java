package io.kenxue.devops.application.application.info.command;

import io.kenxue.devops.application.application.info.assembler.ApplicationInfo2DTOAssembler;
import io.kenxue.devops.coreclient.dto.common.response.Response;
import io.kenxue.devops.domain.repository.application.ApplicationInfoRepository;
import io.kenxue.devops.domain.domain.application.ApplicationInfo;
import io.kenxue.devops.coreclient.dto.application.applicationinfo.ApplicationInfoAddCmd;
import org.springframework.stereotype.Component;
import jakarta.annotation.Resource;
import io.kenxue.devops.coreclient.context.UserThreadContext;
/**
 * 应用
 * @author mikey
 * @date 2021-12-28 22:57:10
 */
@Component
public class ApplicationInfoAddCmdExe {

    @Resource
    private ApplicationInfoRepository applicationInfoRepository;
    @Resource
    private ApplicationInfo2DTOAssembler applicationInfo2DTOAssembler;

    public Response execute(ApplicationInfoAddCmd cmd) {
        ApplicationInfo applicationInfo = applicationInfo2DTOAssembler.toDomain(cmd.getApplicationInfoDTO());
        applicationInfo.create(UserThreadContext.get());
        applicationInfoRepository.create(applicationInfo);
        return Response.success();
    }
}
