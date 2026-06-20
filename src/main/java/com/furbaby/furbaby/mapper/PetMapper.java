package com.furbaby.furbaby.mapper;

import com.furbaby.furbaby.entity.Pet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PetMapper extends BaseMapper<Pet> {
}
