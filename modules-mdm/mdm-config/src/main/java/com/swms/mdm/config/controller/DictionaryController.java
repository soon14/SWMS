package com.swms.mdm.config.controller;

import com.swms.mdm.api.config.IDictionaryApi;
import com.swms.mdm.api.config.dto.DictionaryDTO;
import com.swms.mdm.config.controller.parameter.DictionarySearchParameter;
import com.swms.mdm.config.domain.entity.Dictionary;
import com.swms.mdm.config.domain.repository.DictionaryRepository;
import com.swms.mdm.config.domain.transfer.DictionaryTransfer;
import com.swms.utils.http.Response;
import com.swms.utils.utils.PageHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dictionary")
@Validated
public class DictionaryController {

    @Autowired
    private IDictionaryApi dictionaryApi;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryTransfer dictionaryTransfer;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid DictionaryDTO dictionaryDTO) {
        if (dictionaryDTO.getId() != null && dictionaryDTO.getId() > 0) {
            dictionaryApi.update(dictionaryDTO);
            return Response.success();
        }
        dictionaryApi.save(dictionaryDTO);
        return Response.success();
    }

    @PostMapping("getById")
    public Object getById(@RequestParam Long id) {
        Dictionary dictionary = dictionaryRepository.findById(id);
        return Response.builder().data(dictionaryTransfer.toDTO(dictionary)).build();
    }

    @PostMapping(value = "search")
    public Object search(@RequestBody DictionarySearchParameter parameter) {
        Page<Dictionary> queryResults = dictionaryRepository.search(parameter);
        List<DictionaryDTO> dictionaryDTOS = queryResults.stream().map(v -> dictionaryTransfer.toDTO(v)).toList();
        return Response.builder().data(PageHelper.covertPage(dictionaryDTOS, queryResults.getTotalElements())).build();
    }

    @GetMapping("getAll")
    public Object getAll() {
        List<Dictionary> dictionaries = dictionaryRepository.getAll();
        List<DictionaryDTO> dictionaryDTOS = dictionaries.stream().map(v -> dictionaryTransfer.toDTO(v)).toList();

        Map<String, List<Map<String, String>>> result = new HashMap<>();
        Map<String, List<DictionaryDTO.DictionaryItem>> codeMap = dictionaryDTOS.stream()
            .collect(Collectors.toMap(DictionaryDTO::getCode, DictionaryDTO::getItems));
        codeMap.forEach((k, v) -> {
            List<Map<String, String>> items = v.stream()
                .sorted(Comparator.comparingInt(DictionaryDTO.DictionaryItem::getOrder))
                .map(item -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("label", item.getShowContext());
                    map.put("enumValue", item.getValue());
                    return map;
                }).toList();
            result.put(k, items);
        });
        return Response.builder().data(result).build();
    }

}