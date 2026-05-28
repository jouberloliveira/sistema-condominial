package br.com.condominial.config;

import br.com.condominial.domain.Unidade;
import br.com.condominial.domain.Usuario;
import br.com.condominial.enums.SituacaoUnidade;
import br.com.condominial.repository.UnidadeRepository;
import br.com.condominial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (usuarioRepository.count() > 0) return;

        Unidade u101 = unidadeRepository.save(Unidade.builder()
            .bloco("A").numero("101").andar("1").situacao(SituacaoUnidade.OCUPADA).build());

        Unidade u102 = unidadeRepository.save(Unidade.builder()
            .bloco("A").numero("102").andar("1").situacao(SituacaoUnidade.OCUPADA).build());

        usuarioRepository.save(Usuario.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .role("ROLE_ADMIN")
            .unidade(null)
            .build());

        usuarioRepository.save(Usuario.builder()
            .username("morador101")
            .password(passwordEncoder.encode("morador123"))
            .role("ROLE_USER")
            .unidade(u101)
            .build());

        usuarioRepository.save(Usuario.builder()
            .username("morador102")
            .password(passwordEncoder.encode("morador123"))
            .role("ROLE_USER")
            .unidade(u102)
            .build());

        log.info("Usuários de teste criados: admin / morador101 / morador102 (senha: morador123)");
    }
}
