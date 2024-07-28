package com.alura.forum.service.response.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ResponseSearchCriteriaFactory {

    Map<String, ResponseSearchCriteria> criteriaMap;

    @Autowired
    public ResponseSearchCriteriaFactory(List<ResponseSearchCriteria> criteriaList){
        // Convert the list of criteria into a map for easy lookup by name
        criteriaMap = criteriaList.stream().collect(Collectors.toMap(
                criteria -> criteria.getClass().getSimpleName().replace("ResponseBy", "").replace("SearchCriteria", "").toLowerCase(),
                criteria -> criteria
        ));
    }

    public ResponseSearchCriteria getCriteria(String criteria) {
        // Return the criteria if found, otherwise return null
        return criteriaMap.getOrDefault(criteria.toLowerCase(), null);
    }

}
