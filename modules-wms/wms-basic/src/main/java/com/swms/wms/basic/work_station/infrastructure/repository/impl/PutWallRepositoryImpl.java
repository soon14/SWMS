package com.swms.wms.basic.work_station.infrastructure.repository.impl;

import com.swms.wms.basic.work_station.domain.entity.PutWall;
import com.swms.wms.basic.work_station.domain.repository.PutWallRepository;
import com.swms.wms.basic.work_station.infrastructure.persistence.mapper.PutWallPORepository;
import com.swms.wms.basic.work_station.infrastructure.persistence.po.PutWallPO;
import com.swms.wms.basic.work_station.infrastructure.persistence.transfer.PutWallPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PutWallRepositoryImpl implements PutWallRepository {

    @Autowired
    private PutWallPORepository putWallPORepository;

    @Autowired
    private PutWallPOTransfer putWallPOTransfer;

    @Override
    public void save(PutWall putWall) {
        putWallPORepository.save(putWallPOTransfer.toPO(putWall));
    }

    @Override
    public void saveAll(List<PutWall> putWalls) {
        putWallPORepository.saveAll(putWallPOTransfer.toPOs(putWalls));
    }

    @Override
    public PutWall findById(Long putWallId) {
        PutWallPO putWallPO = putWallPORepository.findById(putWallId).orElseThrow();
        return putWallPOTransfer.toDO(putWallPO);
    }

    @Override
    public List<PutWall> findAllByWorkStationIds(List<Long> workStationIds) {
        List<PutWallPO> putWallPOS = putWallPORepository.findAllByWorkStationIdIn(workStationIds);
        return putWallPOTransfer.toDOs(putWallPOS);
    }

    @Override
    public PutWall findByCodeAndWorkStationId(String putWallCode, Long workStationId) {
        PutWallPO putWallPO = putWallPORepository.findByPutWallCodeAndWorkStationId(putWallCode, workStationId);
        return putWallPOTransfer.toDO(putWallPO);
    }

}
