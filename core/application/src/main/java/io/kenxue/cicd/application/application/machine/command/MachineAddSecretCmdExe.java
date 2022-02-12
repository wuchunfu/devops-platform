package io.kenxue.cicd.application.application.machine.command;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import io.kenxue.cicd.application.application.machine.assembler.MachineInfo2DTOAssembler;
import io.kenxue.cicd.coreclient.dto.application.machineinfo.MachineInfoAddCmd;
import io.kenxue.cicd.coreclient.dto.application.machineinfo.MachineInfoDTO;
import io.kenxue.cicd.coreclient.dto.common.response.Response;
import io.kenxue.cicd.coreclient.exception.code.SSHErrorCode;
import io.kenxue.cicd.domain.domain.application.MachineInfo;
import io.kenxue.cicd.domain.repository.application.MachineInfoRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * 服务器节点测试连接
 *
 * @author mikey
 * @date 2022-02-07 17:55:06
 */
@Component
public class MachineAddSecretCmdExe {

    @Resource
    private MachineInfoRepository machineInfoRepository;

    public Response execute(MachineInfoAddCmd cmd) {
        MachineInfoDTO machineInfoDTO = cmd.getMachineInfoDTO();
        if (StringUtils.isBlank(machineInfoDTO.getAccessKey()))return Response.error("access key must not be null");
        MachineInfo machineInfo = machineInfoRepository.getById(cmd.getMachineInfoDTO().getId());
        JSch jsch = new JSch();
        String ip = machineInfo.getIp();
        Integer port = machineInfo.getPort();
        String accessUsername = machineInfo.getAccessUsername();
        try {
            Session session = jsch.getSession(accessUsername, ip, port);
            session.setPassword(machineInfo.getAccessPassword()); // 设置密码
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config); // 为Session对象设置properties
            session.setTimeout(5000); // 设置timeout时间
            session.connect(); // 通过Session建立链接
            Channel channel=session.openChannel("exec");
            StringBuilder command = new StringBuilder("echo ");
            command.append(machineInfoDTO.getAccessKey());
            command.append(" >> ~/.ssh/authorized_keys");
            ((ChannelExec)channel).setCommand(command.toString());
            channel.connect();
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.of(SSHErrorCode.ADD_SECRET_KEY_ERROR);
        }
        return Response.success();
    }
}
