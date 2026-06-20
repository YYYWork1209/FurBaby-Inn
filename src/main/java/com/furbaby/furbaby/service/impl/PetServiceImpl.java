package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.furbaby.furbaby.dto.PetCreateDTO;
import com.furbaby.furbaby.dto.PetUpdateDTO;
import com.furbaby.furbaby.entity.Pet;
import com.furbaby.furbaby.entity.Vaccine;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.mapper.PetMapper;
import com.furbaby.furbaby.mapper.VaccineMapper;
import com.furbaby.furbaby.service.IPetService;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.PetDetailVO;
import com.furbaby.furbaby.vo.PetVO;
import com.furbaby.furbaby.vo.VaccineVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements IPetService {

    private final VaccineMapper vaccineMapper;
    private final JWTUtils jwtUtils;

    @Override
    public List<PetVO> listMyPets(String token) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));
        List<Pet> pets = this.list(Wrappers.<Pet>lambdaQuery()
                .eq(Pet::getOwnerId, userId)
                .orderByDesc(Pet::getCreateTime));
        return pets.stream().map(this::toPetVO).collect(Collectors.toList());
    }

    @Override
    public PetDetailVO createPet(String token, PetCreateDTO createDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));
        Pet pet = Pet.builder()
                .ownerId(userId)
                .name(createDTO.getName())
                .species(createDTO.getSpecies())
                .breed(createDTO.getBreed())
                .avatar(createDTO.getAvatar())
                .gender(createDTO.getGender())
                .age(createDTO.getAge())
                .weight(createDTO.getWeight())
                .healthNotes(createDTO.getHealthNotes())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        this.save(pet);
        return toPetDetailVO(pet);
    }

    @Override
    public PetDetailVO getPetDetail(Long id) {
        Pet pet = this.getOne(Wrappers.<Pet>lambdaQuery().eq(Pet::getId, id));
        if (pet == null) {
            throw new NoRegisterException("宠物不存在");
        }
        return toPetDetailVO(pet);
    }

    @Override
    public PetDetailVO updatePet(String token, Long id, PetUpdateDTO updateDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));
        Pet pet = this.getOne(Wrappers.<Pet>lambdaQuery().eq(Pet::getId, id));
        if (pet == null) {
            throw new NoRegisterException("宠物不存在");
        }
        if (!pet.getOwnerId().equals(userId)) {
            throw new NoRegisterException("无权修改他人宠物信息");
        }

        if (updateDTO.getName() != null) {
            pet.setName(updateDTO.getName());
        }
        if (updateDTO.getSpecies() != null) {
            pet.setSpecies(updateDTO.getSpecies());
        }
        if (updateDTO.getBreed() != null) {
            pet.setBreed(updateDTO.getBreed());
        }
        if (updateDTO.getAvatar() != null) {
            pet.setAvatar(updateDTO.getAvatar());
        }
        if (updateDTO.getGender() != null) {
            pet.setGender(updateDTO.getGender());
        }
        if (updateDTO.getAge() != null) {
            pet.setAge(updateDTO.getAge());
        }
        if (updateDTO.getWeight() != null) {
            pet.setWeight(updateDTO.getWeight());
        }
        if (updateDTO.getHealthNotes() != null) {
            pet.setHealthNotes(updateDTO.getHealthNotes());
        }
        pet.setUpdateTime(LocalDateTime.now());
        this.updateById(pet);

        return toPetDetailVO(pet);
    }

    @Override
    public Map<String, Boolean> deletePet(String token, Long id) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));
        Pet pet = this.getOne(Wrappers.<Pet>lambdaQuery().eq(Pet::getId, id));
        if (pet == null) {
            throw new NoRegisterException("宠物不存在");
        }
        if (!pet.getOwnerId().equals(userId)) {
            throw new NoRegisterException("无权删除他人宠物");
        }

        this.removeById(id);
        vaccineMapper.delete(Wrappers.<Vaccine>lambdaQuery().eq(Vaccine::getPetId, id));

        return Map.of("success", true);
    }

    private PetVO toPetVO(Pet pet) {
        return PetVO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .breed(pet.getBreed())
                .avatar(pet.getAvatar())
                .age(pet.getAge())
                .gender(pet.getGender() != null ? pet.getGender().name() : null)
                .weight(pet.getWeight())
                .build();
    }

    private PetDetailVO toPetDetailVO(Pet pet) {
        List<Vaccine> vaccines = vaccineMapper.selectList(
                Wrappers.<Vaccine>lambdaQuery().eq(Vaccine::getPetId, pet.getId()));
        List<VaccineVO> vaccineVOs = vaccines.stream()
                .map(v -> VaccineVO.builder().name(v.getName()).date(v.getDate()).build())
                .collect(Collectors.toList());

        return PetDetailVO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .breed(pet.getBreed())
                .avatar(pet.getAvatar())
                .age(pet.getAge())
                .gender(pet.getGender() != null ? pet.getGender().name() : null)
                .weight(pet.getWeight())
                .healthNotes(pet.getHealthNotes())
                .vaccines(vaccineVOs)
                .createTime(pet.getCreateTime())
                .build();
    }
}
