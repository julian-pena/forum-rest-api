package com.alura.forum.service.response.search;

import com.alura.forum.model.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResponseSearchCriteria {

    Page<Response> applyCriteria(Pageable pageable, String value);
}
