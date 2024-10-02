package saudeconectada.fatec.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration}")
    private Long expiration;

    public String generateToken(UserDetails userDetails) {
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails; // Converta para CustomUserDetails
        String cpf = customUserDetails.getUsername();
        String firstName = customUserDetails.getFirstName(); // Obtenha o firstName diretamente
        String subject = cpf; // Use o CPF como subject
        return doGenerateToken(cpf, firstName, subject);
    }


    // Método interno para geração do token JWT usando o JJWT
    private String doGenerateToken(String cpf, String firstName, String subject) {
        Key signingKey = Keys.hmacShaKeyFor(secret.getBytes()); // Usando a chave segura

        // Cria um HashMap de claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("cpf", cpf);
        claims.put("firstName", firstName); // Adicione o firstName como claim

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey) // Usando a chave gerada
                .compact();
    }

    // Método para validar o token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Método para extrair o nome de usuário do token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Método para obter a data de expiração do token
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Método genérico para obter claims do token
    protected <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) { // Alterado para protected
        final Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())) // Usando a chave segura
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    // Método para verificar se o token está expirado
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
