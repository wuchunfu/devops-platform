package io.kenxue.devops.coreclient.dto.sys.applicationconfig;

import io.kenxue.devops.coreclient.dto.common.command.CommonCommand;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.Accessors;

/**
 * 流水线
 * @author mikey
 * @date 2021-12-26 16:56:03
 */
@Data
@Accessors(chain = true)
public class ApplicationConfigAddCmd extends CommonCommand {
    @NotNull
    private ApplicationConfigDTO applicationConfigDTO;
}
