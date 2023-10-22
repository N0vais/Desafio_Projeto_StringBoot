package one.digitalinovation.digitalinovation.Service;

import one.digitalinovation.digitalinovation.Model.Cliente;

public interface ClienteService {
    
    Iterable<Cliente> buscarTodos();
    Cliente buscarPorId(Long id);
    void inserir(Cliente cliente);
    void atualizar(Long id, Cliente cliente);
    void deletar(Long id);
}
