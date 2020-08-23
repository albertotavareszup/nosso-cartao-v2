package br.com.zup.nossocartao.novaproposta;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

import javax.persistence.Embeddable;

@Embeddable
public class Biometria {

	private byte[] digital;
	private LocalDateTime instante = LocalDateTime.now();

	@Deprecated
	public Biometria() {

	}
	
	public Biometria(String digital) {
		this.digital = Base64.getEncoder().encode(digital.getBytes());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(digital);
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
		Biometria other = (Biometria) obj;
		if (!Arrays.equals(digital, other.digital))
			return false;
		return true;
	}
	
	
	
	

}
