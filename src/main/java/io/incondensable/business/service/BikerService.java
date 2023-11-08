package io.incondensable.business.service;

import io.incondensable.business.repository.BikerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author abbas
 */
@Service
@RequiredArgsConstructor
public class BikerService {

    private final BikerRepository bikerRepository;
}
