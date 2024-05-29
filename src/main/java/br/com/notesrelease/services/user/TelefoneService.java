package br.com.notesrelease.services.user;

import br.com.notesrelease.dominio.user.Phone;
import br.com.notesrelease.dominio.user.PhoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelefoneService {

    private PhoneRepository phoneRepository;

    public TelefoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public void deleteAll(List<Phone> phones) {
        phoneRepository.deleteAll(phones);
    }
}
