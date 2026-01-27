package br.com.luxoempassos.config;

import br.com.luxoempassos.exception.NegocioException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Busca o ID da loja no Header da requisição
        String tenantId = request.getHeader("X-Tenant-ID");

        if (tenantId == null || tenantId.isEmpty()) {
            // Lançamos uma exceção clara para o sistema
            throw new NegocioException("Identificador da Loja (X-Tenant-ID) não fornecido ou inválido.");
        }

        TenantContext.setTenantId(tenantId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Limpa o contexto após terminar a requisição (VITAL para não vazar dados)
        TenantContext.clear();
    }
}
