package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.PetCreateDTO;
import com.furbaby.furbaby.dto.PetUpdateDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IPetService;
import com.furbaby.furbaby.vo.PetDetailVO;
import com.furbaby.furbaby.vo.PetVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "宠物管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")
public class PetController {

    private final IPetService petService;

    @Operation(summary = "我的宠物列表", description = "获取当前登录用户的宠物档案列表")
    @GetMapping("/list")
    public Result<List<PetVO>> listMyPets(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        List<PetVO> pets = petService.listMyPets(token);
        return Result.success(pets);
    }

    @Operation(summary = "创建宠物档案", description = "为当前登录用户创建新的宠物档案")
    @PostMapping("/create")
    public Result<PetDetailVO> createPet(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody PetCreateDTO createDTO) {
        String token = authHeader.replace("Bearer ", "");
        PetDetailVO detail = petService.createPet(token, createDTO);
        return Result.success(detail);
    }

    @Operation(summary = "宠物详情", description = "根据宠物ID获取宠物详细信息及疫苗记录")
    @GetMapping("/detail/{id}")
    public Result<PetDetailVO> getPetDetail(@PathVariable Long id) {
        PetDetailVO detail = petService.getPetDetail(id);
        return Result.success(detail);
    }

    @Operation(summary = "更新宠物信息", description = "更新指定宠物的档案信息，仅宠物主人可操作")
    @PutMapping("/update/{id}")
    public Result<PetDetailVO> updatePet(@RequestHeader("Authorization") String authHeader,
                                           @PathVariable Long id,
                                           @RequestBody PetUpdateDTO updateDTO) {
        String token = authHeader.replace("Bearer ", "");
        PetDetailVO detail = petService.updatePet(token, id, updateDTO);
        return Result.success(detail);
    }

    @Operation(summary = "删除宠物档案", description = "删除指定宠物及其疫苗记录，仅宠物主人可操作")
    @DeleteMapping("/delete/{id}")
    public Result<Map<String, Boolean>> deletePet(@RequestHeader("Authorization") String authHeader,
                                                    @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        Map<String, Boolean> result = petService.deletePet(token, id);
        return Result.success(result);
    }

}
