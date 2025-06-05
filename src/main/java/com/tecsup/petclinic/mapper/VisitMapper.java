package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy =  NullValueMappingStrategy.RETURN_DEFAULT)
public class VisitMapper {

    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

    @Mapping(source = "visitDate", target = "visitDate")
    Visit toVisit(VisitDTO visitDTO);

    default Date stringToDate(String dateStr) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date parsed = dateFormat.parse(dateStr);
            date = new Date(parsed.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Mapping(source = "visitDate", target = "visitDate")
    VisitDTO toVisitDTO(Visit visit);

    default String dateToString(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } else {
            return "";
        }
    }

    List<VisitDTO> toVisitDTOList(List<Visit> visitList);

    List<Visit> toVisitList(List<VisitDTO> visitDTOList);
}

