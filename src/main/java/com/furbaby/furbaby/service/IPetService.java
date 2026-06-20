package com.furbaby.furbaby.service;

import com.furbaby.furbaby.dto.PetCreateDTO;
import com.furbaby.furbaby.dto.PetUpdateDTO;
import com.furbaby.furbaby.entity.Pet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.furbaby.furbaby.vo.PetDetailVO;
import com.furbaby.furbaby.vo.PetVO;

import java.util.List;
import java.util.Map;

public interface IPetService extends IService<Pet> {

    List<PetVO> listMyPets(String token);

    PetDetailVO createPet(String token, PetCreateDTO createDTO);

    PetDetailVO getPetDetail(Long id);

    PetDetailVO updatePet(String token, Long id, PetUpdateDTO updateDTO);

    Map<String, Boolean> deletePet(String token, Long id);
}
