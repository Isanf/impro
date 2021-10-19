package com.supernettechnologie.impro.decodage.web;

import com.supernettechnologie.impro.decodage.domain.CodeDatas;
import com.supernettechnologie.impro.decodage.service.DecodeService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/**/api")
public class DecodeRessource {
    private final Logger log = LoggerFactory.getLogger(DecodeRessource.class);
    private final DecodeService decodeService;

    public DecodeRessource(DecodeService decodeService) {
        this.decodeService = decodeService;
    }

    @GetMapping("/decodage")
    @Timed
    public CodeDatas decodeDatas(@RequestParam(required = true) String datas) throws IOException {
        return new CodeDatas();
    }


}

