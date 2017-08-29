package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco = sessao.getPreco();
		TipoDeEspetaculo tipoDeEspetaculo = sessao.getEspetaculo().getTipo();
		Integer totalIngressos = sessao.getTotalIngressos();
		int ingressosReservados = sessao.getIngressosReservados();
		double percentualRestante = (totalIngressos - ingressosReservados)/ totalIngressos.doubleValue();
		
		
		if(tipoDeEspetaculo.equals(TipoDeEspetaculo.CINEMA) || tipoDeEspetaculo.equals(TipoDeEspetaculo.SHOW)) {

			if(percentualRestante  <= 0.05) { 
				preco = preco.add(preco.multiply(BigDecimal.valueOf(0.10)));
			}
		} else if(tipoDeEspetaculo.equals(TipoDeEspetaculo.BALLET) || tipoDeEspetaculo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			if(percentualRestante <= 0.50) { 
				preco = preco.add(preco.multiply(BigDecimal.valueOf(0.20)));
			}
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}