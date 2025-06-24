package com.viniciuscerqueira.agendador_tarefas.business;


import com.viniciuscerqueira.agendador_tarefas.business.dto.TarefasDTO;
import com.viniciuscerqueira.agendador_tarefas.business.mapper.TarefaConverter;
import com.viniciuscerqueira.agendador_tarefas.business.mapper.TarefaUpdateConverter;
import com.viniciuscerqueira.agendador_tarefas.infrastructure.entity.TarefasEntity;
import com.viniciuscerqueira.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.viniciuscerqueira.agendador_tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.viniciuscerqueira.agendador_tarefas.infrastructure.repository.TarefasRepository;
import com.viniciuscerqueira.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
       TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTarefasDTO(tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){
          return tarefaConverter.paraListaTarefasDTO(
                  tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);
        return tarefaConverter.paraListaTarefasDTO(listaTarefas);
    }

    public void deletaTarefaPorId(String id){
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, ID INEXISTENTE" + id, e.getCause());
        }
    }

    public TarefasDTO alteraStatus (StatusNotificacaoEnum status, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTarefasDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return tarefaConverter.paraTarefasDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }



}
