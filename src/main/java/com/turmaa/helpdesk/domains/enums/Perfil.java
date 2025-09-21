package com.turmaa.helpdesk.domains.enums;

/**
 * The Enum Perfil.
 * @author breno.honorio
 */
public enum Perfil {

	/*
	 * Considerando que este enum funciona com um array, a visualização a nível de banco os posicionaria de acordo
	 * com  sequência definida aqui iniciando por 0. Dessa forma, caso fosse adicionado mais um item ao enum, esta
	 * classificação poderia perder a ordem definida inicialmente. A solução mais simoples é adicionar um código
	 * com valor fixo e uma descrição, conforme feito a seguir. Assim a equencia definida inicialmente não se perde
	 * e pode-se adicionar mais perfil ao enum sem prejuízos.
	 */
	
	
	/** The admin. */
	ADMIN(0, "ROLE_ADMIN"),
	
	/** The cliente. */
	CLIENTE(1, "ROLE_CLIENTE"),
	
	/** The tecnico. */
	TECNICO(2, "ROLE_TECNICO");
	
	/** The codigo. */
	private Integer codigo;
	
	/** The descricao. */
	private String descricao;
	

	/**
	 * Instantiates a new perfil.
	 *
	 * @param codigo the codigo
	 * @param descricao the descricao
	 */
	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}
	
	/**
	 * Gets the descricao.
	 *
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	
	/**
	 * To enum.
	 * 
	 * Este método, chamado toEnum, serve como um conversor.
	 * Ele recebe um código numérico (Integer) e o transforma na sua constante de enum Perfil correspondente.
	 * Se o código não existir, ele lança um erro para indicar que o valor é inválido.
	 *  
	 *
	 * @param codigo the codigo
	 * @return the perfil
	 */
	public static Perfil toEnum(Integer codigo) {
		if(codigo == null) {
			return null;
		}
		
		for(Perfil x : Perfil.values()) {
			if(codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		
		/*
		 * Esta exceção trata de argumentos ilegais.
		 */
		throw new IllegalArgumentException("Perfil Inválido");
	}
	
}
