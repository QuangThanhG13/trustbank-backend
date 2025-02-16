package org.example.accoutservice.commands.util.generator.implementation;

import jakarta.transaction.Transactional;
import org.example.accoutservice.commands.util.generator.IdGenerator;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorImpl implements IdGenerator {

    private final CouterRespository couterRespository;

    public IdGeneratorImpl(CouterRespository couterRespository) {
        this.couterRespository = couterRespository;
    }

    @Transactional
    @Override
    public String autoGenerateId() {
       Long id = getNewCounterId();
       return String.format("%09d", id);
    }

    private Long getNewCounterId() {
       Couter savedCouter = couterRespository.save(new Couter());
       return savedCouter.getId();
    }
}
