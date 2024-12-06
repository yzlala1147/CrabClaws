package com.CrabClawsApplication.mapper;

import com.CrabClawsApplication.pojo.SSRF_No;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SSRF_No_Mapper {

    @Select("select * FROM imagepath where imagenumber = #{id}")
    SSRF_No SSRF_No(int id);


}
