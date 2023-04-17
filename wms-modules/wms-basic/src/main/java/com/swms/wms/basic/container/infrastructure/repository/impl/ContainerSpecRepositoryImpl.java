package com.swms.wms.basic.container.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.domain.repository.ContainerSpecRepository;
import com.swms.wms.basic.container.infrastructure.persistence.mapper.ContainerSlotSpecMapper;
import com.swms.wms.basic.container.infrastructure.persistence.mapper.ContainerSpecMapper;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerSlotSpecPO;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerSpecPO;
import com.swms.wms.basic.container.infrastructure.persistence.transfer.ContainerSpecPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerSpecRepositoryImpl implements ContainerSpecRepository {

    @Autowired
    private ContainerSpecMapper containerSpecMapper;

    @Autowired
    private ContainerSlotSpecMapper containerSlotSpecMapper;

    @Autowired
    private ContainerSpecPOTransfer containerSpecPOTransfer;

    @Override
    public ContainerSpec findByContainerSpecCode(String containerSpecCode) {
        LambdaQueryWrapper<ContainerSpecPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContainerSpecPO::getContainerSpecCode, containerSpecCode);
        ContainerSpecPO containerPO = containerSpecMapper.selectOne(wrapper);

        LambdaQueryWrapper<ContainerSlotSpecPO> slotWrapper = new LambdaQueryWrapper<>();
        slotWrapper.eq(ContainerSlotSpecPO::getContainerSpecCode, containerSpecCode);
        List<ContainerSlotSpecPO> containerSlots = containerSlotSpecMapper.selectList(slotWrapper);
        return containerSpecPOTransfer.toContainerSpec(containerPO, containerSlots);
    }
}
