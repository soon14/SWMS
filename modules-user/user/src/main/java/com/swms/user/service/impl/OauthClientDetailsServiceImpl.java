//package com.swms.user.service.impl;
//
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.google.common.base.Joiner;
//import com.swms.user.repository.entity.OauthClientDetails;
//import com.swms.user.repository.mapper.OauthClientDetailsMapper;
//import com.swms.user.rest.param.oauthclient.OauthClientUpdatedParam;
//import com.swms.user.service.OauthClientDetailsService;
//import com.swms.utils.exception.WmsException;
//import com.swms.utils.exception.code_enum.UserErrorDescEnum;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.BeanUtils;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//
///**
// * <p>
// * 授权客户端表 服务实现类
// * </p>
// *
// * @author sws
// * @since 2020-12-25
// */
//@Service
//@AllArgsConstructor
//public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails>
//    implements OauthClientDetailsService {
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void add(OauthClientUpdatedParam param) throws Exception {
//        synchronized (this) {
//            LambdaQueryWrapper<OauthClientDetails> wrapper = Wrappers.<OauthClientDetails>lambdaQuery()
//                .eq(OauthClientDetails::getClientId, param.getClientId());
//            OauthClientDetails oauthClientDetails = this.getOne(wrapper);
//            if (oauthClientDetails != null) {
//                throw new WmsException(UserErrorDescEnum.ERR_AUTHED_CLIENT_ALREADY_EXISTS);
//            }
//            Set<String> authorizedGrantTypes = param.getAuthorizedGrantTypes();
//            if (authorizedGrantTypes == null || authorizedGrantTypes.isEmpty()) {
//                throw new WmsException(UserErrorDescEnum.ERR_EMPTY_AUTH_TYPE);
//            }
//            oauthClientDetails = new OauthClientDetails();
//            BeanUtils.copyProperties(param, oauthClientDetails);
//            String authorizedGrantTypesJoin = Joiner.on(",").skipNulls().join(authorizedGrantTypes);
//            oauthClientDetails.setAuthorizedGrantTypes(authorizedGrantTypesJoin);
//            oauthClientDetails.setClientSecret(passwordEncoder.encode(param.getClientSecret()));
//            save(oauthClientDetails);
//        }
//    }
//
//    @Override
//    public void update(OauthClientUpdatedParam param) throws Exception {
//        Set<String> authorizedGrantTypes = param.getAuthorizedGrantTypes();
//        if (authorizedGrantTypes == null || authorizedGrantTypes.isEmpty()) {
//            throw new WmsException(UserErrorDescEnum.ERR_EMPTY_AUTH_TYPE);
//        }
//        LambdaQueryWrapper<OauthClientDetails> wrapper = Wrappers.<OauthClientDetails>lambdaQuery()
//            .eq(OauthClientDetails::getClientId, param.getClientId());
//        OauthClientDetails entity = this.getOne(wrapper);
//        if (null == entity) {
//            throw new WmsException(UserErrorDescEnum.ERR_CLIENT_DOES_NOT_EXIST);
//        }
//
//        OauthClientDetails oauthClientDetails = new OauthClientDetails();
//        oauthClientDetails.setId(entity.getId());
//        BeanUtils.copyProperties(param, oauthClientDetails);
//        String authorizedGrantTypesJoin = Joiner.on(",").skipNulls().join(authorizedGrantTypes);
//        oauthClientDetails.setAuthorizedGrantTypes(authorizedGrantTypesJoin);
//        String clientSecret = param.getClientSecret();
//        if (StrUtil.isEmpty(clientSecret)) {
//            oauthClientDetails.setClientSecret(null);
//        } else {
//            oauthClientDetails.setClientSecret(passwordEncoder.encode(clientSecret));
//        }
//        updateById(oauthClientDetails);
//    }
//
//    @Override
//    public synchronized void deleteById(String clientId) {
//        synchronized (this) {
//            removeById(clientId);
//        }
//    }
//
//    public static void main(String[] args) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println("encoder.encode(\"123456\") = " + encoder.encode("123456"));
//    }
//}
