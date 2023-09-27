package com.swms.wms.inbound.infrastructure.repository.impl;

import static com.swms.common.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_BOX_NO_ERROR;

import com.swms.common.utils.exception.WmsException;
import com.swms.wms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.wms.inbound.infrastructure.persistence.mapper.InboundPlanOrderDetailPORepository;
import com.swms.wms.inbound.infrastructure.persistence.mapper.InboundPlanOrderPORepository;
import com.swms.wms.inbound.infrastructure.persistence.po.InboundPlanOrderDetailPO;
import com.swms.wms.inbound.infrastructure.persistence.po.InboundPlanOrderPO;
import com.swms.wms.inbound.infrastructure.persistence.transfer.InboundPlanOrderPOTransfer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class InboundPlanOrderRepositoryImpl implements InboundPlanOrderRepository {

    @Autowired
    private InboundPlanOrderPORepository inboundPlanOrderPORepository;

    @Autowired
    private InboundPlanOrderDetailPORepository inboundPlanOrderDetailPORepository;

    @Autowired
    private InboundPlanOrderPOTransfer inboundPlanOrderPOTransfer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderAndDetail(InboundPlanOrder inboundPlanOrder) {
        InboundPlanOrderPO inboundPlanOrderPO = inboundPlanOrderPORepository.save(inboundPlanOrderPOTransfer.toOrderPO(inboundPlanOrder));

        List<InboundPlanOrderDetailPO> inboundPlanOrderDetailPOS = inboundPlanOrderPOTransfer.toDetailPOs(inboundPlanOrder.getDetails());
        inboundPlanOrderDetailPOS.forEach(v -> v.setInboundPlanOrderId(inboundPlanOrderPO.getId()));
        inboundPlanOrderDetailPORepository.saveAll(inboundPlanOrderDetailPOS);
    }

    @Override
    public InboundPlanOrder findById(Long inboundPlanOrderId) {

        InboundPlanOrderPO inboundPlanOrderPO = inboundPlanOrderPORepository.findById(inboundPlanOrderId).orElseThrow();
        List<InboundPlanOrderDetailPO> inboundPlanOrderDetailPOS = inboundPlanOrderDetailPORepository.findByInboundPlanOrderId(inboundPlanOrderId);

        return inboundPlanOrderPOTransfer.toDO(inboundPlanOrderPO, inboundPlanOrderDetailPOS);
    }

    @Override
    public List<InboundPlanOrder> findByCustomerOrderNo(String customerOrderNo) {
        return inboundPlanOrderPOTransfer.toDOs(inboundPlanOrderPORepository.findByCustomerOrderNo(customerOrderNo));
    }

    @Override
    public List<InboundPlanOrder> findInboundPlanOrderByCustomerOrderNos(Collection<String> customerOrderNo) {
        return inboundPlanOrderPORepository.findByCustomerOrderNoIn(customerOrderNo)
            .map(inboundPlanOrderPOTransfer::toDOs)
            .orElseGet(ArrayList::new);
    }

    @Override
    public InboundPlanOrder findByBoxNo(String boxNo, String warehouseCode) {
        List<InboundPlanOrderDetailPO> inboundPlanOrderDetailPOS = inboundPlanOrderDetailPORepository.findByBoxNo(boxNo);

        if (CollectionUtils.isEmpty(inboundPlanOrderDetailPOS)) {
            throw WmsException.throwWmsException(INBOUND_BOX_NO_ERROR, boxNo);
        }

        List<InboundPlanOrderPO> inboundPlanOrderPOS = inboundPlanOrderPORepository.findAllById(inboundPlanOrderDetailPOS.stream()
            .map(InboundPlanOrderDetailPO::getInboundPlanOrderId).toList());
        InboundPlanOrderPO inboundPlanOrderPO = inboundPlanOrderPOS.stream()
            .filter(v -> StringUtils.equals(warehouseCode, v.getWarehouseCode())).findFirst().orElseThrow();

        return inboundPlanOrderPOTransfer.toDO(inboundPlanOrderPO, inboundPlanOrderDetailPOS.stream()
            .filter(v -> Objects.equals(v.getInboundPlanOrderId(), inboundPlanOrderPO.getId())).toList());
    }

    @Override
    public boolean existByBoxNos(Collection<String> boxNos, String warehouseCode) {
        List<InboundPlanOrderDetailPO> inboundPlanOrderDetailPOS = inboundPlanOrderDetailPORepository.findByBoxNoIn(boxNos);

        List<InboundPlanOrderPO> inboundPlanOrderPOS = inboundPlanOrderPORepository.findAllById(inboundPlanOrderDetailPOS
            .stream().map(InboundPlanOrderDetailPO::getInboundPlanOrderId).toList());
        return inboundPlanOrderPOS.stream().anyMatch(v -> StringUtils.equals(warehouseCode, v.getWarehouseCode()));
    }

}
