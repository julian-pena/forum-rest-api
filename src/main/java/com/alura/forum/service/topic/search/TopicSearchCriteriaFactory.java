package com.alura.forum.service.topic.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TopicSearchCriteriaFactory {
    private final Map<String, TopicSearchCriteria> criteriaMap;

    @Autowired
    public TopicSearchCriteriaFactory(List<TopicSearchCriteria> criteriaList) {
        // Convert the list of criteria into a map for easy lookup by name
        criteriaMap = criteriaList.stream().collect(Collectors.toMap(
                criteria -> criteria.getClass().getSimpleName().replace("TopicBy", "").replace("SearchCriteria", "").toLowerCase(),
                criteria -> criteria
        ));
    }

    public TopicSearchCriteria getCriteria(String criteria) {
        // Return the criteria if found, otherwise return null
        return criteriaMap.getOrDefault(criteria.toLowerCase(), null);
    }
}
