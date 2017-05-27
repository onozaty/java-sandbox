package com.example.demo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TestRepository {

    @Insert("INSERT INTO test(number, size) VALUES(#{number}, #{size})")
    public void insert(@Param("number") long number, @Param("size") long size);

    @Insert("INSERT INTO test(number, size) VALUES(#{number}, #{size2})")
    public void insert2(@Param("number") long number, @Param("size2") long size);
}
