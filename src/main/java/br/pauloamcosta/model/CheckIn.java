package br.pauloamcosta.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe de entidade representando os CheckIns.
 * 
 * @author pauloamcosta
 * 
 * @since 1.0.0
 *
 */

@Entity
public class CheckIn implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dataEntrada;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dataSaida;

	private boolean adicionalVeiculo;
	private double valorDiarias;

	public CheckIn() {

	}

	public CheckIn(Long id, Pessoa pessoa, LocalDateTime dataEntrada, LocalDateTime dataSaida,
			boolean adicionalVeiculo) {
		super();
		this.id = id;
		this.pessoa = pessoa;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.adicionalVeiculo = adicionalVeiculo;
	}

	/**
	 * Método que retorna total de dias de hospedagem
	 * 
	 * @author pauloamcosta
	 * 
	 * @param dataEntrada = data da entrada
	 * @param dataSaida   = data da saida
	 * 
	 * @since 1.0.0
	 * 
	 * @return Dias total de hospedagem
	 */
	public Long getDiasHospedagem(LocalDateTime dataEntrada, LocalDateTime dataSaida) {
		return ChronoUnit.DAYS.between(dataEntrada, dataSaida);
	}

	/**
	 * Método calcula valor da hospedagem
	 * 
	 * @author pauloamcosta
	 * 
	 * @since 1.0.0
	 * 
	 * @return Valor da hospedagem
	 */
	
	@JsonIgnoreProperties(value = {"parentActivity"})
	public double getValorDiarias() {
		Long totalDias = getDiasHospedagem(getDataEntrada(), getDataSaida());
		double valorDiaria = 120.0;
		double valorEstacionamento = 15.0;
		if (adicionalVeiculo == true) {
			return (totalDias * valorDiaria) + valorEstacionamento;
		} else {
			return (totalDias * valorDiaria);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) {
		this.dataSaida = dataSaida;
	}

	public boolean isAdicionalVeiculo() {
		return adicionalVeiculo;
	}

	public void setAdicionalVeiculo(boolean adicionalVeiculo) {
		this.adicionalVeiculo = adicionalVeiculo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckIn other = (CheckIn) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setValorDiarias(double valorDiarias) {
		this.valorDiarias = valorDiarias;
	}

}
