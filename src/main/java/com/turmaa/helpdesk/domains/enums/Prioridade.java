package com.turmaa.helpdesk.domains.enums;

/**
 * The Enum Prioridade.
 * Define a prioridade de atendimento de um chamado.
 * 
 * BAIXA -> Chamado de menor urgência.
 * MEDIA -> Chamado com urgência moderada.
 * ALTA -> Chamado com prioridade máxima.
 * 
 * author
 */
public enum Prioridade {

    /** The baixa. */
    BAIXA(0, "BAIXA"),

    /** The media. */
    MEDIA(1, "MEDIA"),

    /** The alta. */
    ALTA(2, "ALTA");

    /** The codigo. */
    private Integer codigo;

    /** The descricao. */
    private String descricao;

    /**
     * Instantiates a new prioridade.
     *
     * @param codigo the codigo
     * @param descricao the descricao
     */
    private Prioridade(Integer codigo, String descricao) {
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
     * @param codigo the codigo
     * @return the prioridade
     */
    public static Prioridade toEnum(Integer codigo) {
        if(codigo == null) {
            return null;
        }

        for(Prioridade x : Prioridade.values()) {
            if(codigo.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Prioridade Inválida");
    }
}
