package com.maispartners.datamais.services;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public abstract class BaseCrudService<Domain, ID extends Serializable> {

    private CrudRepository<Domain, ID> crudRepository;

    public BaseCrudService(CrudRepository<Domain, ID> crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Domain salvar(Domain domain) {
        return this.crudRepository.save(domain);
    }

    public Iterable<Domain> salvar(Iterable<Domain> domains) {
        return this.crudRepository.saveAll(domains);
    }

    // TODO: (ID) Avaliar se é a melhor estratégia
    public Domain obter(ID id) {
        return this.crudRepository.findById(id)
                .orElse(null);
    }

    public void deletar(ID id) {
        this.crudRepository.deleteById(id);
    }

    public void deletar(Domain domain) {
        this.crudRepository.delete(domain);
    }

    public void deletar(Iterable<Domain> domains) {
        this.crudRepository.deleteAll(domains);
    }

    public void deletarTodos() {
        this.crudRepository.deleteAll();
    }

    public Iterable<Domain> obter() {
        return this.crudRepository.findAll();
    }

    public Iterable<Domain> obter(Iterable<ID> ids) {
        return this.crudRepository.findAllById(ids);
    }

    public long contar() {
        return this.crudRepository.count();
    }

    public boolean existe(ID id) {
        return this.crudRepository.existsById(id);
    }
}