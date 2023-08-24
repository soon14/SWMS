package com.swms.common.utils.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String currentUser = UserContext.getCurrentUser();
        if (StringUtils.isEmpty(currentUser)) {
            return Optional.of(RpcContext.getServerAttachment().getAttachment(AuthConstants.USERNAME));
        }
        return Optional.of(currentUser);
    }
}
