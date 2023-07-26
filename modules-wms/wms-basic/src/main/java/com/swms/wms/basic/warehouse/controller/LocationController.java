package com.swms.wms.basic.warehouse.controller;

import static com.swms.utils.exception.code_enum.BasicErrorDescEnum.FORBIDDEN_OPERATE_MULTIPLE_AISLE;

import com.google.common.collect.Lists;
import com.swms.utils.exception.WmsException;
import com.swms.utils.http.Response;
import com.swms.wms.api.basic.IContainerSpecApi;
import com.swms.wms.api.basic.ILocationApi;
import com.swms.wms.api.basic.IWarehouseAreaApi;
import com.swms.wms.api.basic.dto.AisleDTO;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.api.basic.dto.LocationDTO;
import com.swms.wms.api.basic.dto.LocationUpdateDTO;
import com.swms.wms.api.basic.dto.WarehouseAreaDTO;
import com.swms.wms.basic.warehouse.controller.parameter.AisleLocationQueryVO;
import com.swms.wms.basic.warehouse.controller.parameter.AisleLocationVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("warehouseArea")
@Validated
public class LocationController {

    @Autowired
    private ILocationApi locationApi;

    @Autowired
    private IWarehouseAreaApi iWarehouseAreaApi;

    @Autowired
    private IContainerSpecApi iContainerSpecApi;

    @PostMapping("create")
    public Object create(@RequestBody @Valid AisleLocationVO locationVO) {
        createLocations(locationVO);
        return Response.success();
    }

    @PostMapping("update")
    public Object update(@RequestBody @Valid LocationUpdateDTO locationUpdateDTO) {
        locationApi.update(locationUpdateDTO);
        return Response.success();
    }

    @GetMapping("{id}")
    public Object delete(@PathVariable Long id) {
        locationApi.delete(id);
        return Response.success();
    }

    @PostMapping("getByAisle")
    public Object getByAisle(@RequestBody AisleLocationQueryVO aisleLocationQueryVO) {
        return Response.builder()
            .data(locationApi.getByAisle(aisleLocationQueryVO.getAisleCode(), aisleLocationQueryVO.getWarehouseAreaId()))
            .build();
    }

    @PostMapping("updateAisleLocation")
    public Object updateAisleLocation(@RequestBody @Valid AisleLocationVO aisleLocationVO) {
        Set<String> aisleCodes = aisleLocationVO.getLocationDescList().stream()
            .map(AisleLocationVO.LocationDesc::getAisleCode).collect(Collectors.toSet());
        if (aisleCodes.size() != 1) {
            throw WmsException.throwWmsException(FORBIDDEN_OPERATE_MULTIPLE_AISLE);
        }

        locationApi.deleteByAisle(aisleCodes.iterator().next(), aisleLocationVO.getWarehouseAreaId());
        createLocations(aisleLocationVO);

        return Response.success();
    }

    private void createLocations(@RequestBody @Valid AisleLocationVO aisleLocationVO) {
        WarehouseAreaDTO warehouseAreaDTO = iWarehouseAreaApi.getById(aisleLocationVO.getWarehouseAreaId());
        List<AisleDTO> aisleDTOS = Lists.newArrayList();
        List<LocationDTO> locationDTOS = Lists.newArrayList();
        buildLocationDTOS(aisleLocationVO, warehouseAreaDTO, aisleDTOS, locationDTOS);
        locationApi.createLocations(aisleDTOS, locationDTOS);
    }

    private void buildLocationDTOS(AisleLocationVO locationVO, WarehouseAreaDTO warehouseAreaDTO, List<AisleDTO> aisleDTOS, List<LocationDTO> locationDTOS) {
        locationVO.getLocationDescList()
            .stream().collect(Collectors.groupingBy(AisleLocationVO.LocationDesc::getAisleCode))
            .forEach((key, values) -> {
                aisleDTOS.add(AisleDTO.builder()
                    .aisleCode(key)
                    .singleEntrance(values.get(0).isSingleEntrance())
                    .warehouseAreaId(locationVO.getWarehouseAreaId()).build());

                locationDTOS.addAll(values.stream().flatMap(value -> {
                    value.setWarehouseAreaGroupCode(warehouseAreaDTO.getWarehouseGroupCode());
                    value.setWarehouseAreaCode(warehouseAreaDTO.getWarehouseAreaCode());
                    ContainerSpecDTO containerSpecDTO =
                        iContainerSpecApi.getContainerSpecDTO(value.getContainerSpecCode(), locationVO.getWarehouseCode());
                    return buildLocationDTO(containerSpecDTO, value, locationVO);
                }).toList());

            });
    }

    private Stream<LocationDTO> buildLocationDTO(ContainerSpecDTO containerSpecDTO, AisleLocationVO.LocationDesc value,
                                                 AisleLocationVO locationVO) {
        return containerSpecDTO.getContainerSlotSpecs().stream()
            .flatMap(containerSlotSpec ->
                IntStream.range(1, value.getShelfNumber() + 1)
                    .mapToObj(i ->
                        LocationDTO.builder()
                            .locationCode(value.generateLocationCode(i, containerSlotSpec.getLocBay(), containerSlotSpec.getLocLevel()))
                            .locationType(locationVO.getLocationType())
                            .aisleCode(value.getAisleCode())
                            .occupied(false)
                            .shelfCode(value.generateShelfCode(i))
                            .warehouseCode(locationVO.getWarehouseCode())
                            .warehouseLogicId(value.getWarehouseLogicId())
                            .warehouseAreaId(locationVO.getWarehouseAreaId())
                            .build()));
    }


}
