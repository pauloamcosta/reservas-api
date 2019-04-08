package br.pauloamcosta.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

	static int DIARIA_SEMANA = 120;
	static int DIARIA_FDS = 150;
	static int DIARIA_EXTRA = 120;

	static int ADD_GARAGEM_SEMANA = 15;
	static int ADD_GARAGEM_FDS = 20;

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
	 * Método calcula valor da hospedagem. Verifica dia da semana e adicional de garagem para cálculo.
	 * Verifica se hora da saída ultrapassa determinada hora. Se sim, um valor extra é adicionado.
	 * 
	 * @author pauloamcosta
	 * 
	 * @since 1.0.0
	 * 
	 * @return Valor da hospedagem
	 */

	@JsonIgnoreProperties(value = { "parentActivity" })
	public double getValorDiarias() {

		List<String> totalDatas = allDaysName();
		List<Integer> valor = new ArrayList<>();
		for (String diaSemana : totalDatas) {
			if (diaSemana == "SUNDAY" || diaSemana == "SATURDAY") {
				if (adicionalVeiculo) {
					valor.add(DIARIA_FDS + ADD_GARAGEM_FDS);
				} else {
					valor.add(DIARIA_FDS);
				}
			} else {
				if (adicionalVeiculo) {
					valor.add(DIARIA_SEMANA + ADD_GARAGEM_SEMANA);
				} else {
					valor.add(DIARIA_SEMANA);
				}
			}
		}
		if (isAfterTime(dataSaida)) {	
			valor.add(DIARIA_EXTRA);
		}
		
		int somaDiarias = valor.stream().mapToInt(Integer::intValue).sum();
		System.out.println(somaDiarias);
		System.out.println(totalDatas);
		System.out.println(dataSaida.getHour());

		return somaDiarias;

	}
	
	/**
	 * Método que verifica se um LocalDateTime ultrapassou as 16:00
	 * 
	 * @author pauloamcosta
	 * 
	 * @param dataChecada = data a ser checada.
	 * 
	 * @since 1.0.0
	 * 
	 * @return Boolean informando se ultrapassou o horario ou não
	 */

	public boolean isAfterTime(LocalDateTime dataChecada) {
			if (dataChecada.getHour() >= 16) {
				return true;
			}		
		return false;
	}
	
	/**
	 * Método cria uma lista com o nome dos dias da semana de um período de tempo
	 * @author pauloamcosta
	 * 
	 * @since 1.0.0
	 * 
	 * @return Lista com nome das datas de um período
	 */


	public List<String> allDaysName() {

		List<String> totalDatas = new ArrayList<>();
		while (!dataEntrada.isAfter(dataSaida)) {
			totalDatas.add(dataEntrada.getDayOfWeek().name());
			dataEntrada = dataEntrada.plusDays(1);
		}
		return totalDatas;
	}
	
	/**
	 * Método que verifica se hospedagem está ativa.
	 * valida se a hora atual está no range de tempo de entrada e saída.
	 * 
	 * @author pauloamcosta
	 * 
	 * @since 1.0.0
	 * 
	 * @return Boolean informado se a hospedagem está ativa ou não
	 */
	public boolean getHospedagemAtiva() {
		LocalDateTime agora = LocalDateTime.now();
		return isWithinRange(agora);
	}
	
	/**
	 * Método que verifica se um LocalDateTime está em um determinado período de tempo.
	 * @author pauloamcosta
	 * 
	 * @since 1.0.0
	 * 
	 * @return Boolean se está presente ou não.
	 */
	boolean isWithinRange(LocalDateTime testDate) {
		return testDate.isBefore(dataSaida) || testDate.isAfter(dataEntrada);
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

	}

}
