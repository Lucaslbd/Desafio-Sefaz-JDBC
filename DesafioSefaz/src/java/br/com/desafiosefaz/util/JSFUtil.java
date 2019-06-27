package br.com.desafiosefaz.util;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class JSFUtil {

    public static void mensagemSucesso(String mensagem) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext externalContext = contexto.getExternalContext();
        Flash flash = externalContext.getFlash();
        flash.setKeepMessages(true);
        contexto.addMessage(null, msg);
    }

    public static void mensagemErro(String mensagem) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext externalContext = contexto.getExternalContext();
        Flash flash = externalContext.getFlash();
        flash.setKeepMessages(true);
        contexto.addMessage(null, msg);
    }
    
    public static void mensagemAviso(String mensagem) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, mensagem);
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext externalContext = contexto.getExternalContext();
        Flash flash = externalContext.getFlash();
        flash.setKeepMessages(true);
        contexto.addMessage(null, msg);
    }

    public static String getParam(String nome) {
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext externalContext = contexto.getExternalContext();
        Map<String, String> parametros = externalContext.getRequestParameterMap();
        String valor = parametros.get(nome);
        return valor;
    }
}
