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

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface VisitMapper {

    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

    @Mapping(source = "visitDate", target = "visitDate", dateFormat = "yyyy-MM-dd")
    Visit toVisit(VisitDTO visitDTO);

    @Mapping(source = "visitDate", target = "visitDate", dateFormat = "yyyy-MM-dd")
    VisitDTO toVisitDTO(Visit visit);

    List<Visit> toVisitList(List<VisitDTO> visitDTOList);

    List<VisitDTO> toVisitDTOList(List<Visit> visitList);

    default Date stringToDate(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsed = dateFormat.parse(dateStr);
                return new Date(parsed.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    default String dateToString(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        }
        return null;
    }
}
