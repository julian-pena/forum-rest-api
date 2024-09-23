package com.alura.forum.service.course.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CourseSearchCriteriaFactory {

    private final Map<String, CourseSearchCriteria> criteriaMap;

    @Autowired
    public CourseSearchCriteriaFactory(List<CourseSearchCriteria> criteriaList){
        criteriaMap = criteriaList.stream().collect(Collectors.toMap(
                criteria -> criteria.getClass().getSimpleName().replace("CourseBy", "").replace("SearchCriteria", "").toLowerCase(),
                criteria -> criteria
        ));
    }

    public CourseSearchCriteria getCriteria(String criteria){
        return criteriaMap.getOrDefault(criteria.toLowerCase(), null);
    }

    public Map<String, CourseSearchCriteria> getMap(){
        return criteriaMap;
    }



}
