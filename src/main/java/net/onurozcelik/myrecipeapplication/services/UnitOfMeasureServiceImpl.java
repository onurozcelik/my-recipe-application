package net.onurozcelik.myrecipeapplication.services;

import net.onurozcelik.myrecipeapplication.commands.UnitOfMeasureCommand;
import net.onurozcelik.myrecipeapplication.converters.UnitOfMeasureToUnitOfMeasureCommand;
import net.onurozcelik.myrecipeapplication.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureRepository uomRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.uomRepository = uomRepository;
        this.converter = converter;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAllUoMs() {
        Set<UnitOfMeasureCommand> commands = new HashSet<>();
        uomRepository.findAll().forEach(uom -> commands.add(converter.convert(uom)));
        return commands;
    }
}
