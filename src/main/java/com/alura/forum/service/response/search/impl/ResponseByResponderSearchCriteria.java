package com.alura.forum.service.response.search.impl;

import com.alura.forum.model.entity.Response;
import com.alura.forum.repository.ResponseRepository;
import com.alura.forum.service.response.search.ResponseSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ResponseByResponderSearchCriteria implements ResponseSearchCriteria {

    private final ResponseRepository responseRepository;

    @Autowired
    public ResponseByResponderSearchCriteria(ResponseRepository responseRepository){
        this.responseRepository = responseRepository;
    }


    @Override
    public Page<Response> applyCriteria(Pageable pageable, String value) {
        return responseRepository.findByResponderNameContainingIgnoreCase(value, pageable);
    }
}
