package io.kenxue.devops.coreclient.dto.sys.applicationinfo;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.Accessors;
import io.kenxue.devops.coreclient.dto.common.command.CommonCommand;
/**
 * 应用
 * @author mikey
 * @date 2021-12-26 16:56:03
 */
@Data
@Accessors(chain = true)
public class ApplicationInfoUpdateCmd extends CommonCommand {

    @NotNull
    private ApplicationInfoDTO applicationInfoDTO;
}
