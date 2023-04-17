package com.swms.wms.basic.container.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.swms.wms.basic.container.domain.entity.Container;
import com.swms.wms.basic.container.domain.repository.ContainerRepository;
import com.swms.wms.basic.container.infrastructure.persistence.mapper.ContainerMapper;
import com.swms.wms.basic.container.infrastructure.persistence.mapper.ContainerSlotMapper;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerPO;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerSlotPO;
import com.swms.wms.basic.container.infrastructure.persistence.transfer.ContainerPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerRepositoryImpl implements ContainerRepository {

    @Autowired
    private ContainerMapper containerMapper;

    @Autowired
    private ContainerSlotMapper containerSlotMapper;

    @Autowired
    private ContainerPOTransfer containerPOTransfer;

    @Override
    public Container findByContainerCode(String containerCode) {
        LambdaQueryWrapper<ContainerPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContainerPO::getContainerCode, containerCode);
        ContainerPO containerPO = containerMapper.selectOne(wrapper);

        LambdaQueryWrapper<ContainerSlotPO> containerPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        containerPOLambdaQueryWrapper.eq(ContainerSlotPO::getContainerCode, containerCode);
        List<ContainerSlotPO> containerSlots = containerSlotMapper.selectList(containerPOLambdaQueryWrapper);
        return containerPOTransfer.toContainer(containerPO, containerSlots);
    }
}
