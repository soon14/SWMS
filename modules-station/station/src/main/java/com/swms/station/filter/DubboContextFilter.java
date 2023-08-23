package com.swms.station.filter;

import com.swms.common.utils.user.AuthConstants;
import com.swms.common.utils.user.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

@Activate
@Slf4j
public class DubboContextFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        RpcContext rpcContext = RpcContext.getClientAttachment();
        try {
            String currentUser = UserContext.getCurrentUser();
            rpcContext.setAttachment(AuthConstants.USERNAME, currentUser);
            return invoker.invoke(invocation);
        } finally {
            rpcContext.removeAttachment(AuthConstants.USERNAME);
        }
    }
}
