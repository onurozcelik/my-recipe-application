package net.onurozcelik.myrecipeapplication.services;

import net.onurozcelik.myrecipeapplication.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> findAllUoMs();
}
