package com.curriculosatt.diamond.demo.domain.curriculos.entity;

import com.curriculosatt.diamond.demo.domain.curriculos.enums.CandidatoRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Table(name = "candidato")
@Entity(name="candidato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Candidato implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "data_nascimento")
    private java.sql.Date dataNascimento;

    @Column(nullable = false)
    private String email;

    private String telefone;

    private String escolaridade;

    private String funcao;

    @ElementCollection
    @CollectionTable(name = "competencias", joinColumns = @JoinColumn(name = "candidato_id"))
    @MapKeyColumn(name = "competencia")
    @Column(name = "nivel_proficiencia")
    private Map<String, Integer> competencias;


    @Column(name = "status_solicitacao", nullable = false)
    private String statusSolicitacao;

    @JsonIgnore
    @ManyToMany(mappedBy = "candidatos")
    private List<Vaga> vagas;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(255)")
    private CandidatoRole role;

    public Candidato(String cpf, CandidatoRole role) {
        this.cpf = cpf;
        this.role = role;
    }

    public Candidato(String cpf, String role) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
        System.out.println("Authorities: " + authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}