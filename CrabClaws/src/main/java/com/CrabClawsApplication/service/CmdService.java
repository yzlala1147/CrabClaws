package com.CrabClawsApplication.service;

import com.CrabClawsApplication.pojo.Result;
import org.springframework.stereotype.Service;

@Service
public interface CmdService {

    Result execCommand(String command);

    Result ProcessBuilderCommand(String command);

}
