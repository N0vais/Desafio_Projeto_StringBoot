package one.digitalinovation.digitalinovation.Service.Implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinovation.digitalinovation.Model.ClientRepository;
import one.digitalinovation.digitalinovation.Model.Cliente;
import one.digitalinovation.digitalinovation.Model.Endereco;
import one.digitalinovation.digitalinovation.Model.EnderecoRepository;
import one.digitalinovation.digitalinovation.Service.ClienteService;
import one.digitalinovation.digitalinovation.Service.ViaCepService;

@Service
public class ClienteServiceImplementation implements ClienteService{
    //injetando componentes do spring via @Autowired.
    //padrao de projeto sigleton;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService ViaCepService;

    @Override
    public Iterable<Cliente> buscarTodos(){
        //busca todos os cliente .
        return clientRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clientRepository.findById(id);
        return cliente.get();
    }    

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
     }

    private void salvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(()-> {
            Endereco novoEndereco = ViaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clientRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteDoBancoDeDados = clientRepository.findById(id);
        if(clienteDoBancoDeDados.isPresent()){
            salvarClienteComCep(cliente);
        }else{
            throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
        }
    }

    @Override
    public void deletar(Long id) {
        clientRepository.deleteById(id);
    }
}
