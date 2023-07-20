package com.swms.mdm.config.controller;

import com.swms.mdm.api.config.IDictionaryApi;
import com.swms.mdm.api.config.dto.DictionaryDTO;
import com.swms.mdm.config.domain.entity.Dictionary;
import com.swms.mdm.config.domain.repository.DictionaryRepository;
import com.swms.mdm.config.domain.transfer.DictionaryTransfer;
import com.swms.utils.dictionary.IEnum;
import com.swms.utils.http.Response;
import jakarta.validation.Valid;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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

    @GetMapping("{id}")
    public Object getById(@PathVariable Long id) {
        Dictionary dictionary = dictionaryRepository.findById(id);
        return Response.builder().data(dictionaryTransfer.toDTO(dictionary)).build();
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
                    map.put("value", item.getValue());
                    return map;
                }).toList();
            result.put(k, items);
        });
        return Response.builder().data(result).build();
    }

    @GetMapping("refresh")
    public <E> Object refresh() {
        Reflections reflections = new Reflections("com.swms");
        Set<Class<?>> dictionaryEnums = reflections.getTypesAnnotatedWith(com.swms.utils.dictionary.Dictionary.class);

        List<DictionaryDTO> dictionaryDTOS = dictionaryEnums.stream().filter(v -> v.getSimpleName().endsWith("Enum"))
            .map(cClass -> {
                Object[] enumConstants = cClass.getEnumConstants();

                AtomicInteger index = new AtomicInteger(0);
                List<DictionaryDTO.DictionaryItem> items = Arrays.stream(enumConstants).map(enumConstant -> {
                    DictionaryDTO.DictionaryItem item = new DictionaryDTO.DictionaryItem();
                    String value = null;
                    String label = null;
                    if (cClass.getInterfaces().length > 0) {
                        try {
                            Method labelM = cClass.getMethod("getLabel");
                            Method valueM = cClass.getMethod("getValue");
                            value = valueM.invoke(enumConstant).toString();
                            label = labelM.invoke(enumConstant).toString();
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else {
                        value = enumConstant.toString();
                        label = enumConstant.toString();
                    }
                    item.setDefaultItem(index.getAndIncrement() == 0);
                    item.setShowContext(label);
                    item.setValue(value);

                    return item;
                }).toList();

                String simpleName = cClass.getSimpleName();
                DictionaryDTO dictionaryDTO = new DictionaryDTO();
                dictionaryDTO.setCode(simpleName.substring(0, simpleName.indexOf("Enum")));
                dictionaryDTO.setName(simpleName);
                dictionaryDTO.setEditable(true);
                dictionaryDTO.setItems(items);

                return dictionaryDTO;
            }).toList();
        dictionaryRepository.saveAll(dictionaryTransfer.toDOS(dictionaryDTOS));
        return Response.success();
    }

}
