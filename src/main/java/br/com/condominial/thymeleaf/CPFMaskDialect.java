package br.com.condominial.thymeleaf;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.Set;

@Component
public class CPFMaskDialect extends AbstractDialect implements IExpressionObjectDialect {

    public CPFMaskDialect() {
        super("cpfMask");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("cpf");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                if ("cpf".equals(expressionObjectName)) {
                    return new CPFMaskHelper();
                }
                return null;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }
}
