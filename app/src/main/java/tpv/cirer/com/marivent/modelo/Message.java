package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 31/03/2017.
 */

public class Message {
    private String MessageGrupo;
    private String MessageEmpresa;
    private String MessageLocal;
    private String MessageSeccion;
    private String MessageMesa;
    private int MessageId;
    private int MessageActivo;
    private String MessageCreado;
    private String MessageCaja;
    private int MessageComensales;

    public Message(){}
    public String getMessageGrupo() {
        return MessageGrupo;
    }
    public void setMessageGrupo(String messageGrupo) {
        this.MessageGrupo = messageGrupo;
    }

    public String getMessageEmpresa() {
        return MessageEmpresa;
    }
    public void setMessageEmpresa(String messageEmpresa) {
        this.MessageEmpresa = messageEmpresa;
    }

    public String getMessageSeccion() {
        return MessageSeccion;
    }
    public void setMessageSeccion(String messageSeccion) {
        this.MessageSeccion = messageSeccion;
    }

    public String getMessageLocal() {
        return MessageLocal;
    }
    public void setMessageLocal(String messageLocal) {
        this.MessageLocal = messageLocal;
    }

    public String getMessageCaja() {
        return MessageCaja;
    }
    public void setMessageCaja(String messageCaja) {
        this.MessageCaja = messageCaja;
    }

    public String getMessageCreado() {
        return MessageCreado;
    }

    public void setMessageCreado(String messageCreado) {
        this.MessageCreado = messageCreado;
    }


    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int mesaId) {
        this.MessageId = mesaId;
    }

    public int getMessageActivo() {
        return MessageActivo;
    }

    public void setMessageActivo(int mesaActivo) {
        this.MessageActivo = mesaActivo;
    }


    public String getMessageMesa() {
        return MessageMesa;
    }

    public void setMessageMesa(String messageMesa) {
        this.MessageMesa = messageMesa;
    }
    public int getMessageComensales() {
        return MessageComensales;
    }
    public void setMessageComensales(int cabeceraComensales) {
        this.MessageComensales = cabeceraComensales;
    }


}
