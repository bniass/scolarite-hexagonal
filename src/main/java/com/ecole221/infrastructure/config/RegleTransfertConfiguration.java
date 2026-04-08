package com.ecole221.infrastructure.config;

import com.ecole221.domain.policy.inscription.RegleTransfert;
import com.ecole221.domain.policy.inscription.RegleTransfertSousDelai;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegleTransfertConfiguration {

    @Bean
    public RegleTransfert regleTransfert(
            @Value("${scolarite.transfert.delai-max-mois}")
            int delaiMaxMois
    ) {
        return new RegleTransfertSousDelai(delaiMaxMois);
    }
}

