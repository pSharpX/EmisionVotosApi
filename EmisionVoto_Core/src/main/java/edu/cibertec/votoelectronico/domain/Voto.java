package edu.cibertec.votoelectronico.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import edu.cibertec.votoelectronico.domain.listener.VotoListener;

@Entity
@Table(name = "votos")
@EntityListeners(VotoListener.class)
@NamedNativeQueries({
		@NamedNativeQuery(name = "QN_VOTO_DELETE_ALL", query = "db.votos.drop()", resultClass = Voto.class) })
public class Voto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Type(type = "objectid")
	private String votoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "grupoPolitico" )
	private GrupoPolitico grupoPolitico;

	private String dni;
	private Date fecha;
	private String hash;

	public Voto() {
		super();
	}

	public String getVotoId() {
		return votoId;
	}

	public void setVotoId(String votoId) {
		this.votoId = votoId;
	}

	public GrupoPolitico getGrupoPolitico() {
		return grupoPolitico;
	}

	public void setGrupoPolitico(GrupoPolitico grupoPolitico) {
		this.grupoPolitico = grupoPolitico;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getData() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return dni + df.format(fecha);
	}

	@Override
	public String toString() {
		return "Voto [votoId=" + votoId + ", dni=" + dni + ", fecha=" + fecha + "]";
	}

}
