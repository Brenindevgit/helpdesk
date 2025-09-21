package com.turmaa.helpdesk.domains.enums;


/**
 * The Enum Status.
 * Representa o estado atual de um chamado.
 * 
 * ABERTO -> Chamado foi criado mas ainda não atendido.
 * ANDAMENTO -> Chamado está em progresso.
 * ENCERRADO -> Chamado já foi finalizado.
 * 
 * @author
 */
public enum Status {

    /** The aberto. */
    ABERTO(0, "ABERTO"),

    /** The andamento. */
    ANDAMENTO(1, "ANDAMENTO"),

    /** The encerrado. */
    ENCERRADO(2, "ENCERRADO");

    /** The codigo. */
    private Integer codigo;

    /** The descricao. */
    private String descricao;

    /**
     * Instantiates a new status.
     *
     * @param codigo the codigo
     * @param descricao the descricao
     */
    private Status(Integer codigo, String descricao) {
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
     * @return the status
     */
    public static Status toEnum(Integer codigo) {
        if(codigo == null) {
            return null;
        }

        for(Status x : Status.values()) {
            if(codigo.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Status Inválido");
    }
}