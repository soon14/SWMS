package com.swms.mdm.config.controller;

import com.swms.mdm.api.config.IBarcodeParseRuleApi;
import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import com.swms.mdm.config.domain.entity.BarcodeParseRule;
import com.swms.mdm.config.domain.repository.BarcodeParseRuleRepository;
import com.swms.mdm.config.domain.transfer.BarcodeParseRuleTransfer;
import com.swms.utils.http.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("barcodeParseRule")
@Validated
public class BarcodeParseRuleController {

    @Autowired
    private IBarcodeParseRuleApi iBarcodeParseRuleAPi;

    @Autowired
    private BarcodeParseRuleRepository barcodeParseRuleRepository;

    @Autowired
    private BarcodeParseRuleTransfer barcodeParseRuleTransfer;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid BarcodeParseRuleDTO barcodeParseRuleDTO) {
        if (barcodeParseRuleDTO.getId() != null && barcodeParseRuleDTO.getId() > 0) {
            iBarcodeParseRuleAPi.update(barcodeParseRuleDTO);
            return Response.success();
        }
        iBarcodeParseRuleAPi.save(barcodeParseRuleDTO);
        return Response.success();
    }

    @GetMapping("{id}")
    public Object getById(@PathVariable Long id) {
        BarcodeParseRule barcodeParseRule = barcodeParseRuleRepository.findById(id);
        return Response.builder().data(barcodeParseRuleTransfer.toDTO(barcodeParseRule)).build();
    }
}
