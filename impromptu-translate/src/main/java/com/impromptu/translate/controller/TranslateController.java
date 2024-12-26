package com.impromptu.translate.controller;

import com.common.result.ResultUtil;
import com.common.result.ResultVO;
import com.impromptu.translate.dto.TranslateDTO;
import com.impromptu.translate.enums.LanguageEnum;
import com.impromptu.translate.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 翻译接口
 * @author 石鹏
 * @date 2024/11/13 17:35
 */
@RestController
@RequestMapping("/translate")
public class TranslateController {

    @Autowired
    private TranslateService translateService;


    /**
     * 语言列表
     * @return
     */
    @GetMapping("/languageList")
    public ResultVO<?> languageList() {
        return ResultUtil.success(LanguageEnum.toList());
    }

    /**
     * 翻译
     * @param translateDTO
     * @return
     */
    @PostMapping()
    public ResultVO<?> translate(@RequestBody TranslateDTO translateDTO) {
        return ResultUtil.success(translateService.translate(translateDTO));
    }

    public static void main(String[] args) {
        TranslateController translateController = new TranslateController();
        ResultVO<?> resultVO = translateController.languageList();
        System.out.println(resultVO);
    }
}
