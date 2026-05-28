package br.com.condominial.config;

import br.com.condominial.domain.Morador;
import br.com.condominial.domain.Unidade;
import br.com.condominial.repository.MoradorRepository;
import br.com.condominial.repository.UnidadeRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityConverters {

    @Component
    public static class StringToUnidade implements Converter<String, Unidade> {
        private final UnidadeRepository repository;

        public StringToUnidade(UnidadeRepository repository) {
            this.repository = repository;
        }

        @Override
        public Unidade convert(String source) {
            if (source == null || source.isBlank()) return null;
            try {
                return repository.findById(Long.parseLong(source.trim())).orElse(null);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    @Component
    public static class StringToMorador implements Converter<String, Morador> {
        private final MoradorRepository repository;

        public StringToMorador(MoradorRepository repository) {
            this.repository = repository;
        }

        @Override
        public Morador convert(String source) {
            if (source == null || source.isBlank()) return null;
            try {
                return repository.findById(Long.parseLong(source.trim())).orElse(null);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }
}
