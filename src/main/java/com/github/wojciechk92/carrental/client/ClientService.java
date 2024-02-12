package com.github.wojciechk92.carrental.client;

import com.github.wojciechk92.carrental.client.dto.ClientReadModel;
import com.github.wojciechk92.carrental.client.dto.ClientWriteModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

 List<ClientReadModel> getClients(Pageable pageable);

 ClientReadModel getClient(Long id);

 ClientReadModel createClient(ClientWriteModel employee);

}
