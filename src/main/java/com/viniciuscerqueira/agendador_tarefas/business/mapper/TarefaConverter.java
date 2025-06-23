package com.viniciuscerqueira.agendador_tarefas.business.mapper;

import com.viniciuscerqueira.agendador_tarefas.business.dto.TarefasDTO;
import com.viniciuscerqueira.agendador_tarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO(TarefasEntity tarefasEntity);

}
