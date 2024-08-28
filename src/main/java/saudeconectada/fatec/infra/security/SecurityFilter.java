package saudeconectada.fatec.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HealthcareUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestPath = request.getServletPath();

        // Permitir acesso sem autenticação nas rotas de login
        if ("/api/healthprofessional/login".equals(requestPath) || "/api/patient/login".equals(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Recuperar token do cabeçalho da requisição
        String token = recoverToken(request);
        if (token != null) {
            String cpf = tokenService.getUsernameFromToken(token);
            if (cpf != null) {
                UserDetails user;
                try {
                    System.out.println("Tentando carregar paciente com CPF: " + cpf);
                    user = userDetailsService.loadPatientByCpf(cpf);
                } catch (UsernameNotFoundException e) {
                    System.out.println("Paciente não encontrado. Tentando carregar profissional de saúde.");
                    user = userDetailsService.loadHealthProfessionalByCpf(cpf);
                }

                if (user != null && tokenService.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    // Metodo para recuperar o token do cabeçalho da requisição
    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Retorna o token sem "Bearer "
        }
        return null; // Retorna null se não encontrar um token válido
    }
}
